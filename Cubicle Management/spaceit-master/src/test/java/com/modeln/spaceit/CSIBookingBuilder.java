package com.modeln.spaceit;

import com.modeln.spaceit.entities.CSIBooking;
import com.modeln.spaceit.enums.CSIBookingStatus;

public class CSIBookingBuilder {
    CSIBooking csiBooking;
    CSIBookingBuilder(){
        csiBooking=new CSIBooking();
    }
    public CSIBookingBuilder withBookingId(Long id){
        csiBooking.setBookingId(id);
        return this;
    }
    public CSIBookingBuilder withStatus(CSIBookingStatus status){
        csiBooking.setStatus(status);
        return this;
    }
    public CSIBooking build(){
        return csiBooking;
    }
}
