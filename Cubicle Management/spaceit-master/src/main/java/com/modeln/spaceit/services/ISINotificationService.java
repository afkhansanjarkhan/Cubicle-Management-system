package com.modeln.spaceit.services;

import com.modeln.spaceit.entities.CSICubicle;
import com.modeln.spaceit.entities.CSINotification;
import com.modeln.spaceit.exceptions.CSINotificationNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ISINotificationService {

    List<CSINotification> getNotifications(Specification<CSINotification> spec);

    CSINotification getNotificationById(Long id);

    void updateRead(Long id) throws CSINotificationNotFoundException;

    void deleteNotification(Long notificationId);

}
