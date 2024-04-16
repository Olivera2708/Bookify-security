package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.NotificationDTO;
import rs.ac.uns.ftn.Bookify.model.Notification;

import java.time.format.DateTimeFormatter;

@Component
public class NotificationDTOMapper {

    private static ModelMapper modelMapper;

    public NotificationDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public NotificationDTO toNotificationDTO(Notification notification){
        NotificationDTO notificationDTO = modelMapper.map(notification, NotificationDTO.class);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        notificationDTO.setCreated(notification.getCreated().format(dateTimeFormatter));
        return notificationDTO;
    }

}
