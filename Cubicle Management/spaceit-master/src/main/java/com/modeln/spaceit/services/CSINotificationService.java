package com.modeln.spaceit.services;

import com.modeln.spaceit.entities.CSICubicle;
import com.modeln.spaceit.entities.CSINotification;
import com.modeln.spaceit.exceptions.CSICubicleNotFoundException;
import com.modeln.spaceit.exceptions.CSINotificationNotFoundException;
import com.modeln.spaceit.repositories.ISINotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CSINotificationService implements ISINotificationService{

    @Autowired
    ISINotificationRepository notificationRepository;

    @Override
    public List<CSINotification> getNotifications(Specification<CSINotification> spec){
        List<CSINotification> notifications = new ArrayList<CSINotification>();
        notificationRepository.findAll(spec).forEach(notifications::add);
        return notifications;
    }

    @Override
    public CSINotification getNotificationById(Long id) {
        // findById is provided by jpa by default
        return notificationRepository.findById(id).get();
    }
    @Override
    public void updateRead(Long id ) throws CSINotificationNotFoundException{
        CSINotification notificationFromDb = notificationRepository.findById(id).get();
        System.out.println(notificationFromDb.toString());
        if(notificationFromDb == null){
            throw new CSINotificationNotFoundException("Correct Notification Not Found");
        }
        notificationFromDb.setRead(true);
        notificationRepository.save(notificationFromDb);
    }

    @Override
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
