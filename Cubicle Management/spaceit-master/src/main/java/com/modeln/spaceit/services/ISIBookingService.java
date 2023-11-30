package com.modeln.spaceit.services;

import com.modeln.spaceit.entities.CSIBooking;
import com.modeln.spaceit.exceptions.CSIBookingAlreadyDone;
import com.modeln.spaceit.exceptions.CSIBookingNotFoundException;
import com.modeln.spaceit.exceptions.CSICubicleAlreadyDoneException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ISIBookingService {

    List<CSIBooking> getBookings(Specification<CSIBooking> spec);

    List<CSIBooking> getBooking(Specification<CSIBooking> spec);
    CSIBooking getBookingById(Long bookingId);
    CSIBooking insert(CSIBooking booking) throws CSICubicleAlreadyDoneException, CSIBookingAlreadyDone;

    CSIBooking updateBooking(Long bookingId,CSIBooking booking) throws CSIBookingNotFoundException;
    void deleteBooking(Long bookingId);


}
