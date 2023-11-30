package com.modeln.spaceit.controllers;

import com.modeln.spaceit.entities.CSIBooking;
import com.modeln.spaceit.entities.Demo;
import com.modeln.spaceit.exceptions.CSIBookingAlreadyDone;
import com.modeln.spaceit.exceptions.CSIBookingNotFoundException;
import com.modeln.spaceit.exceptions.CSICubicleAlreadyDoneException;
import com.modeln.spaceit.services.ISIBookingService;
import com.modeln.spaceit.utils.CSiSpecificationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/booking")
public class CSIBookingController {

    @Autowired
    ISIBookingService bookingService;

    @GetMapping
    public ResponseEntity<List<CSIBooking>> getAllBooking(@RequestParam(value = "search", required = false) String search) {
        CSiSpecificationBuilder<CSIBooking> builder = new CSiSpecificationBuilder<>();
        Pattern pattern = Pattern.compile("(\\w+?)(~|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<CSIBooking> spec = builder.build();
        List<CSIBooking> bookings = bookingService.getBookings(spec);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    @GetMapping({"/{bookingId}"})
    public ResponseEntity<CSIBooking> getBookings(@PathVariable Long bookingId) {
        return new ResponseEntity<>(bookingService.getBookingById(bookingId), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<CSIBooking> saveBooking(@RequestBody CSIBooking booking) throws CSICubicleAlreadyDoneException {
        CSIBooking booking1 = null;
        Logger logger = LoggerFactory.getLogger(CSIBookingController.class);
        try {
            booking1 = bookingService.insert(booking);
        }
        catch (CSICubicleAlreadyDoneException e){
            logger.error("An error i.e.Cubicle is already booked");
            return new ResponseEntity<>(HttpStatus
                    .ALREADY_REPORTED);
        }
        catch(CSIBookingAlreadyDone e){
            //logger
            logger.error("An error i.e. Booking is already done for the given id");
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
        return new ResponseEntity<>(booking1, HttpStatus.CREATED);
    }
    @PutMapping({"/{bookingId}"})
    public ResponseEntity<CSIBooking> updateBooking(@PathVariable("bookingId") Long bookingId, @RequestBody CSIBooking booking) throws CSIBookingNotFoundException {
        try {
            bookingService.updateBooking(bookingId, booking);
        }
        catch(CSIBookingNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookingService.getBookingById(bookingId), HttpStatus.OK);
    }
    @DeleteMapping({"/{bookingId}"})
    public ResponseEntity<Demo> deleteBooking(@PathVariable("bookingId") Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
