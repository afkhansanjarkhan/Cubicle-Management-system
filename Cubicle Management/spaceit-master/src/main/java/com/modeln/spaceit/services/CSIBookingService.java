package com.modeln.spaceit.services;

import com.modeln.spaceit.entities.CSIBooking;
import com.modeln.spaceit.entities.CSICubicle;
import com.modeln.spaceit.entities.CSIEmployee;
import com.modeln.spaceit.exceptions.CSIBookingAlreadyDone;
import com.modeln.spaceit.exceptions.CSIBookingNotFoundException;
import com.modeln.spaceit.exceptions.CSICubicleAlreadyDoneException;
import com.modeln.spaceit.repositories.ISIBookingRepository;
import com.modeln.spaceit.repositories.ISICubicleRepository;
import com.modeln.spaceit.repositories.ISIEmployeeRepository;
import com.modeln.spaceit.utils.CSIAuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CSIBookingService implements ISIBookingService {

    @Autowired
    ISIBookingRepository bookingRepository;
    @Autowired
    CSIAuthenticationUtil authenticationUtil;

    @Autowired
    ISICubicleRepository cubicleRepository;

    @Autowired
    ISIEmployeeRepository employeeRepository;

    @Autowired
    CSIBookingNotificationService bookingNotificationService;

    CSIEmployee employee;

    @Override
    public List<CSIBooking> getBookings(Specification<CSIBooking> spec) {
        List<CSIBooking> bookings = new ArrayList<>();
        bookingRepository.findAll(spec).forEach(bookings::add);
        return bookings;
    }

    @Override
    public List<CSIBooking> getBooking(Specification<CSIBooking> spec) {
        return null;
    }

    @Override
    public CSIBooking getBookingById(Long bookingId) {
        // findById is provided by jpa by default
        return bookingRepository.findById(bookingId).get();
    }
    @Override
    public CSIBooking insert(CSIBooking booking) throws CSICubicleAlreadyDoneException,CSIBookingAlreadyDone{
        //booking.setEmployee(authenticationUtil.getLoggedInUser());
        String cubicleId=booking.getCubicle().getCubicleId();
        long employeeId=booking.getEmployee().getEmployeeId();
        long cId= cubicleRepository.findByCId(cubicleId);
        CSICubicle cubicleFromDB= cubicleRepository.findById(cId).get();
        long eId= employeeRepository.findByEId(employeeId);
        CSIEmployee employeeFromDB = employeeRepository.findById(eId).get();
        booking.setCubicle(cubicleFromDB);
        booking.setEmployee(employeeFromDB);
        checkUserExistence(booking);
        checkCubicleExistence(booking);
        booking = bookingRepository.save(booking);
        bookingNotificationService.newBookingCreationNotificationService(booking);
        return booking;
    }

    @Override
    public CSIBooking updateBooking(Long id, CSIBooking booking) throws CSIBookingNotFoundException {
        CSIBooking bookingFromDb = bookingRepository.findById(id).get();
        System.out.println(bookingFromDb.toString());
       if(bookingFromDb == null) {
           throw new CSIBookingNotFoundException("Booking not found for given Id");
       }
        bookingFromDb.setBookingId(booking.getBookingId());
        bookingFromDb.setStatus(booking.getStatus());
        bookingRepository.save(bookingFromDb);
        return bookingFromDb;
    }

    public void checkUserExistence(CSIBooking booking) throws CSIBookingAlreadyDone{
        List<CSIBooking> existingBooking = bookingRepository.findByEmployeeAndStartDate(booking.getEmployee(),booking.getStartDate());
        if(existingBooking.size() != 0){
            throw new CSIBookingAlreadyDone("Booking is already done for given Id");
        }
    }
        public void checkCubicleExistence(CSIBooking booking) throws CSICubicleAlreadyDoneException {
            List<CSICubicle> existingCubicle=bookingRepository.findByCubicleAndStartDate(booking.getCubicle(),booking.getStartDate());
            if(existingCubicle.size()!=0){
                throw new CSICubicleAlreadyDoneException("Cubicle is already booked, try booking other cubicle");
            }
        }
        @Override
        public void deleteBooking(Long bookingId){
            bookingRepository.deleteById(bookingId);
        }


}