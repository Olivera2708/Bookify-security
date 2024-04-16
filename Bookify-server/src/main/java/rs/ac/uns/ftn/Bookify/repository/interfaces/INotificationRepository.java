package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.Bookify.model.Notification;

import java.util.List;

public interface INotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT u.notifications FROM User u WHERE u.id=:userId")
    public List<Notification> getNotificationByUserId(Long userId);

    @Query("SELECT n FROM User u JOIN u.notifications n WHERE u.id = :userId AND n.seen = false")
    public List<Notification> getUnseenNotificationForUser(Long userId);

}
