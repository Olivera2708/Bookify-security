package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.NotificationDTO;
import rs.ac.uns.ftn.Bookify.dto.NotificationSettingsDTO;
import rs.ac.uns.ftn.Bookify.enumerations.NotificationType;
import rs.ac.uns.ftn.Bookify.exception.BadRequestException;
import rs.ac.uns.ftn.Bookify.mapper.NotificationDTOMapper;
import rs.ac.uns.ftn.Bookify.model.*;
import rs.ac.uns.ftn.Bookify.repository.interfaces.INotificationRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.INotificationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    INotificationRepository notificationRepository;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    IUserService userService;

    @Autowired
    NotificationDTOMapper mapper;

    @Override
    public List<Notification> getUserNotification(Long userId) {
        List<Notification> notifications = notificationRepository.getNotificationByUserId(userId);
        notifications.sort(Comparator.comparing(Notification::getCreated).reversed());
        return notifications;
    }

    @Override
    public boolean removeNotification(Long userId, Long notificationId) {
        return false;
    }

    @Override
    public NotificationSettingsDTO getNotificationSettings(Long userId) {
        NotificationSettingsDTO settings = new NotificationSettingsDTO();
        User user = userService.get(userId);
        Map<NotificationType, Boolean> t;
        if(user instanceof Owner)
            t = ((Owner) user).getNotificationType();
        else if (user instanceof Guest)
            t = ((Guest) user).getNotificationType();
        else
            throw new BadRequestException("Administrator doesn't have notification settings");
        settings.setNotificationType(t);
        return settings;

    }

    @Override
    public NotificationSettingsDTO updateNotificationSettings(Long userId, NotificationSettingsDTO updatedSettings) {
        User user = userService.get(userId);
        if(user instanceof Owner)
            ((Owner) user).setNotificationType(updatedSettings.getNotificationType());
        else if (user instanceof Guest)
            ((Guest) user).setNotificationType(updatedSettings.getNotificationType());
        else throw new BadRequestException("Administrator doesn't have notification settings");
        userService.save(user);
        return updatedSettings;
    }

    @Override
    public List<Notification> getUnseenNotifications(Long userId) {
        List<Notification> response = new ArrayList<>();
        this.notificationRepository.getUnseenNotificationForUser(userId).forEach(notification -> {
            notification.setSeen(true);
            notificationRepository.save(notification);
            response.add(notification);
        });
        return response;
    }

    @Override
    public void sendNewNotification(Notification notification, Long userId) {
        NotificationDTO notificationDTO = mapper.toNotificationDTO(notification);
        this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + userId, notificationDTO);
    }

    @Override
    public void saveNotification(Notification notification, Long userId) {
        User user = userService.get(userId);
        if(wantsToBeNotified(notification,user)) {
            this.notificationRepository.save(notification);
            saveToUser(user, notification);
            sendNewNotification(notification, userId);
        }
    }

    private void saveToUser(User user, Notification notification) {
        user.getNotifications().add(notification);
        this.userService.save(user);
    }

    private boolean wantsToBeNotified(Notification notification, User user) {
        if(user instanceof Owner)
            return ((Owner) user).getNotificationType().getOrDefault(notification.getNotificationType(), false);
        if(user instanceof Guest)
            return ((Guest) user).getNotificationType().getOrDefault(notification.getNotificationType(), false);
        return false;
    }

    @Override
    public Notification createNotificationOwnerNewReservation(Reservation reservation) {
        Accommodation accommodation = reservation.getAccommodation();
        Long ownerId = userService.findbyAccommodationId(accommodation.getId()).getId();
        String guest = reservation.getGuest().getFirstName() + " " + reservation.getGuest().getLastName();
        String description = guest + " created a new request for accommodation: " + accommodation.getName();
        Notification notification = generateNotification(description, NotificationType.RESERVATION_CREATED);
        saveNotification(notification, ownerId);
        return notification;
    }

    @Override
    public Notification createNotificationOwnerReservationCancellation(Reservation reservation) {
        Accommodation accommodation = reservation.getAccommodation();
        Long ownerId = userService.findbyAccommodationId(accommodation.getId()).getId();
        String guest = reservation.getGuest().getFirstName() + " " + reservation.getGuest().getLastName();
        String description = guest + " cancelled a reservation for accommodation: " + accommodation.getName();
        Notification notification = generateNotification(description, NotificationType.RESERVATION_CANCELED);
        saveNotification(notification, ownerId);
        return notification;
    }

    @Override
    public Notification createNotificationOwnerGotNewRating(Guest guest, Owner owner) {
        String guestName = guest.getFirstName() + " " + guest.getLastName();
        String description = guestName + " add new rating to your profile";
        Notification notification = generateNotification(description, NotificationType.NEW_USER_RATING);
        saveNotification(notification, owner.getId());
        return notification;
    }

    @Override
    public Notification createNotificationOwnerAccommodationGotNewRating(Guest guest, Accommodation accommodation) {
        Long ownerId = userService.findbyAccommodationId(accommodation.getId()).getId();
        String guestName = guest.getFirstName() + " " + guest.getLastName();
        String description = guestName + " add new rating to your accommodation: " + accommodation.getName();
        Notification notification = generateNotification(description, NotificationType.NEW_ACCOMMODATION_RATING);
        saveNotification(notification, ownerId);
        return notification;

    }

    @Override
    public Notification createNotificationGuestRequestResponse(Reservation reservation) {
        Long guestId = reservation.getGuest().getId();
        String description = "Owner responded to request for " + reservation.getAccommodation().getName() + " with " + reservation.getStatus();
        Notification notification = generateNotification(description, NotificationType.RESERVATION_RESPONSE);
        saveNotification(notification, guestId);
        return notification;
    }

    private Notification generateNotification(String description, NotificationType notificationType) {
        Notification notification = new Notification();
        notification.setSeen(false);
        notification.setCreated(LocalDateTime.now());
        notification.setDescription(description);
        notification.setNotificationType(notificationType);
        return notification;
    }


}
