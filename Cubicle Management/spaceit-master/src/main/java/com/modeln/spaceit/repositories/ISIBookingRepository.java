package com.modeln.spaceit.repositories;

import com.modeln.spaceit.entities.CSIBooking;
import com.modeln.spaceit.entities.CSICubicle;
import com.modeln.spaceit.entities.CSIEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ISIBookingRepository extends JpaRepository<CSIBooking, Long>, JpaSpecificationExecutor<CSIBooking> {
    List<CSIBooking> findByEmployeeAndStartDate(CSIEmployee employee, Date startDate);
    List<CSICubicle> findByCubicleAndStartDate(CSICubicle cubicle, Date startDate);

}
