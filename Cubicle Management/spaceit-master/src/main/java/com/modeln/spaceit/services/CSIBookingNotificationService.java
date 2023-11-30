package com.modeln.spaceit.services;

import com.modeln.spaceit.entities.CSIBooking;
import com.modeln.spaceit.entities.CSINotification;
import com.modeln.spaceit.enums.CSINotifyObj;
import com.modeln.spaceit.enums.CSINotifyType;
import com.modeln.spaceit.notifications.CSIEmailNotificationChannel;
import com.modeln.spaceit.notifications.CSINotificationBuilder;
import com.modeln.spaceit.notifications.CSINotificationHandler;
import com.modeln.spaceit.notifications.ISINotificationChannel;
import com.modeln.spaceit.repositories.ISIEmployeeRepository;
import com.modeln.spaceit.repositories.ISINotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CSIBookingNotificationService {

    @Autowired
    ISIEmployeeRepository employeeRepository;

    @Autowired
    ISINotificationRepository notificationRepository;

    final String NEW_CUBICLE_TITLE = "New Booking has been created";

    final String NEW_CUBICLE_DESCRIPTION = "Notification for Create Booking";

    public void newBookingCreationNotificationService(CSIBooking booking){

        ISINotificationChannel notificationChannel = new CSIEmailNotificationChannel();
        CSINotification notification = new CSINotificationBuilder()
                .withTitle(NEW_CUBICLE_TITLE)
                .withDescription(NEW_CUBICLE_DESCRIPTION)
                .withRead(false)
                .withNotifyType(CSINotifyType.BOOKING)
                .withNotifyObj(CSINotifyObj.booking)
                .withObj(booking.getBookingId())
                .withTargetUser(employeeRepository.findByEmployeeName("Admin").get(0)).build();
        notificationRepository.save(notification);
        CSINotificationHandler handler = new CSINotificationHandler();
        handler.addChannel(notificationChannel);
        handler.sendNotification(notification);
    }
}


