package com.modeln.spaceit.notifications;

import com.modeln.spaceit.entities.CSINotification;

import java.util.ArrayList;
import java.util.List;

public class CSINotificationHandler {

    List<ISINotificationChannel> channelList = new ArrayList<>();

    public  void sendNotification(CSINotification notification){
        channelList.forEach(channel->{
            channel.send(notification);
        });
    }

    public void addChannel(ISINotificationChannel notificationChannel) {
        channelList.add(notificationChannel);
    }
}
