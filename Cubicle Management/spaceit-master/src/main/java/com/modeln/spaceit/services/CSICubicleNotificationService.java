package com.modeln.spaceit.services;

import com.modeln.spaceit.entities.CSICubicle;
import com.modeln.spaceit.entities.CSINotification;
import com.modeln.spaceit.enums.CSINotifyObj;
import com.modeln.spaceit.enums.CSINotifyType;
import com.modeln.spaceit.notifications.CSIEmailNotificationChannel;
import com.modeln.spaceit.notifications.CSINotificationBuilder;
import com.modeln.spaceit.notifications.CSINotificationHandler;
import com.modeln.spaceit.notifications.ISINotificationChannel;
import com.modeln.spaceit.repositories.DemoRepository;
import com.modeln.spaceit.repositories.ISICubicleRepository;
import com.modeln.spaceit.repositories.ISIEmployeeRepository;
import com.modeln.spaceit.repositories.ISINotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.activation.DataHandler;

@Service
public class CSICubicleNotificationService {

    @Autowired
    ISIEmployeeRepository employeeRepository;

    @Autowired
    ISINotificationRepository notificationRepository;

    final String NEW_CUBICLE_TITLE = "New cubicle has been created";

    final String NEW_CUBICLE_DESCRIPTION = "Notification for Create cubicle";



    public void newCreateCubicleNotification(CSICubicle cubicle){
        ISINotificationChannel notificationChannel = new CSIEmailNotificationChannel();
        CSINotification notification = new CSINotificationBuilder()
                .withTitle(NEW_CUBICLE_TITLE)
                .withDescription(NEW_CUBICLE_DESCRIPTION)
                .withRead(false)
                .withNotifyType(CSINotifyType.CUBICLE)
                .withNotifyObj(CSINotifyObj.cubicle)
                .withObj(cubicle.getId())
                .withTargetUser(employeeRepository.findByEmployeeName("Admin").get(0)).build();
        notificationRepository.save(notification);
        CSINotificationHandler handler = new CSINotificationHandler();
        handler.addChannel(notificationChannel);
        handler.sendNotification(notification);
    }
}
