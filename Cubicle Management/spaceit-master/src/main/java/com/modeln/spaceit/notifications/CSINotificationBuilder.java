package com.modeln.spaceit.notifications;

import com.modeln.spaceit.entities.CSIEmployee;
import com.modeln.spaceit.entities.CSINotification;
import com.modeln.spaceit.enums.CSINotifyObj;
import com.modeln.spaceit.enums.CSINotifyType;

import java.sql.Time;
import java.sql.Timestamp;

public class CSINotificationBuilder {

    CSINotification notification;

    public CSINotificationBuilder(){
        notification = new CSINotification();
    }

    public CSINotificationBuilder withTitle(String title) {
        notification.setTitle(title);
        return this;
    }

    public  CSINotificationBuilder withTargetUser(CSIEmployee employee){
        notification.setTargetUser(employee);
        return  this;
    }

    public CSINotificationBuilder withDescription(String description){
        notification.setDescription(description);
        return this;
    }

    public CSINotificationBuilder withRead(boolean read){
        notification.setRead(read);
        return this;
    }

    public CSINotificationBuilder withNotifyType(CSINotifyType notifyType){
        notification.setNotifyType(notifyType);
        return this;
    }

    public CSINotificationBuilder withNotifyObj(CSINotifyObj notifyObj){
        notification.setNotifyObj(notifyObj);
        return this;
    }

    public CSINotificationBuilder withObj(Long obj){
        notification.setObj(obj);
        return this;
    }

    public CSINotification build(){
        return  notification;
    }
}
