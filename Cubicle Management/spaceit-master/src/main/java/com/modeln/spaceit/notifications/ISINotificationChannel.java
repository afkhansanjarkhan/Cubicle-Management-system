package com.modeln.spaceit.notifications;

import com.modeln.spaceit.entities.CSINotification;

public interface ISINotificationChannel {

    public void send(CSINotification notification);
}
