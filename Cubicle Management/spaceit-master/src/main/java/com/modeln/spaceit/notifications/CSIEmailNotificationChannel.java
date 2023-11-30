package com.modeln.spaceit.notifications;

import com.modeln.spaceit.entities.CSINotification;

import java.util.logging.Logger;

public class CSIEmailNotificationChannel implements ISINotificationChannel{

    CSINotification notification;

    static Logger  logger = Logger.getLogger(String.valueOf(CSIEmailNotificationChannel.class));

    @Override
    public void send(CSINotification notification) {
        //Email services will be built in future
        logger.info("Email notification ");
    }
}
