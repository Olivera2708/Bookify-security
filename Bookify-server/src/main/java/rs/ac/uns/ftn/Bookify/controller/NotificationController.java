package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.NotificationDTO;
import rs.ac.uns.ftn.Bookify.dto.NotificationSettingsDTO;
import rs.ac.uns.ftn.Bookify.mapper.NotificationDTOMapper;
import rs.ac.uns.ftn.Bookify.service.interfaces.INotificationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationDTOMapper mapper;

    @Autowired
    private INotificationService notificationService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('MANAGE_NOTIFICATIONS')")
    public ResponseEntity<Collection<NotificationDTO>> getUserNotifications(@PathVariable Long userId) {
        List<NotificationDTO> notifications = new ArrayList<>();
        notificationService.getUserNotification(userId).forEach(n -> {
            notifications.add(mapper.toNotificationDTO(n));
        });
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/unseen/{userId}")
    @PreAuthorize("hasAuthority('MANAGE_NOTIFICATIONS')")
    public ResponseEntity<Collection<NotificationDTO>> getUnseenUserNotifications(@PathVariable Long userId) {
        List<NotificationDTO> notifications = new ArrayList<>();
        notificationService.getUnseenNotifications(userId).forEach(n -> {
            notifications.add(mapper.toNotificationDTO(n));

        });
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{notificationId}")
    @PreAuthorize("hasAuthority('MANAGE_NOTIFICATIONS')")
    public ResponseEntity<String> removeNotification(@PathVariable Long userId, @PathVariable Long notificationId) {
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("/{userId}/settings")
    @PreAuthorize("hasAuthority('MANAGE_NOTIFICATIONS')")
    public ResponseEntity<NotificationSettingsDTO> getNotificationSettings(@PathVariable Long userId) {
        Optional<NotificationSettingsDTO> settings = Optional.ofNullable(notificationService.getNotificationSettings(userId));
        return settings.map(notificationSettingsDTO -> new ResponseEntity<>(notificationSettingsDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{userId}/settings")
    @PreAuthorize("hasAuthority('MANAGE_NOTIFICATIONS')")
    public ResponseEntity<NotificationSettingsDTO> updateNotificationSettings(@PathVariable Long userId, @RequestBody NotificationSettingsDTO updatedSettings) {
        Optional<NotificationSettingsDTO> settings = Optional.ofNullable(notificationService.updateNotificationSettings(userId, updatedSettings));
        return settings.map(notificationSettingsDTO -> new ResponseEntity<>(notificationSettingsDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

}
