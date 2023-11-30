package com.modeln.spaceit.controllers;

import com.modeln.spaceit.entities.CSICubicle;
import com.modeln.spaceit.entities.CSINotification;
import com.modeln.spaceit.entities.Demo;
import com.modeln.spaceit.exceptions.CSICubicleNotFoundException;
import com.modeln.spaceit.exceptions.CSINotificationNotFoundException;
import com.modeln.spaceit.services.ISINotificationService;
import com.modeln.spaceit.utils.CSiSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
@RequestMapping("/notification")
public class CSINotificationController {

    @Autowired
    ISINotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<CSINotification>> getAllNotification(@RequestParam(value = "search", required = false) String search) {
        CSiSpecificationBuilder<CSINotification> builder = new CSiSpecificationBuilder<>();
        Pattern pattern = Pattern.compile("(\\w+?)(~|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<CSINotification> spec = builder.build();
        List<CSINotification> notifications = notificationService.getNotifications(spec);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PutMapping({"/markread/{notificationId}"})
    public ResponseEntity<CSINotification> updateRead(@PathVariable("notificationId") Long notificationId) throws CSINotificationNotFoundException {
        try {
            notificationService.updateRead(notificationId);
        }
        catch(CSINotificationNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(notificationService.getNotificationById(notificationId), HttpStatus.OK);
    }

    @DeleteMapping({"/{notificationId}"})
    public ResponseEntity<Demo> deleteNotification(@PathVariable("notificationId") Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
