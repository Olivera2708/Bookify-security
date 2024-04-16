package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.dto.NotificationDTO;
import rs.ac.uns.ftn.Bookify.dto.NotificationSettingsDTO;
import rs.ac.uns.ftn.Bookify.model.*;

import java.util.Collection;
import java.util.List;

public interface INotificationService {
    public List<Notification> getUserNotification(Long userId);
    public boolean removeNotification(Long userId, Long notificationId);
    public NotificationSettingsDTO getNotificationSettings(Long userId);
    public NotificationSettingsDTO updateNotificationSettings(Long userId, NotificationSettingsDTO updatedSettings);
    public List<Notification> getUnseenNotifications(Long userId);
    public void sendNewNotification(Notification notification, Long userId);
    public void saveNotification(Notification notification, Long userId);
    public Notification createNotificationOwnerNewReservation(Reservation reservation);
    public Notification createNotificationOwnerReservationCancellation(Reservation reservation);
    public Notification createNotificationOwnerGotNewRating(Guest guest, Owner owner);
    public Notification createNotificationOwnerAccommodationGotNewRating(Guest guest, Accommodation accommodation);
    public Notification createNotificationGuestRequestResponse(Reservation reservation);
}
