package com.modeln.spaceit.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modeln.spaceit.enums.CSIBookingStatus;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "booking")
public class CSIBooking extends CSIBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cubicle_id",referencedColumnName = "id")
    @JsonIgnoreProperties("bookingList")
    private CSICubicle cubicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id",referencedColumnName = "id")
    @JsonIgnoreProperties("bookingList")
    private CSIEmployee employee;

    private Timestamp startDate;

    private Timestamp endDate;

    @Enumerated(EnumType.STRING)
    private CSIBookingStatus status;



    public CSIEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(CSIEmployee employee) {
        this.employee = employee;
    }

    public CSICubicle getCubicle() {
        return cubicle;
    }

    public void setCubicle(CSICubicle cubicle) {
        this.cubicle = cubicle;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public CSIBookingStatus getStatus() {
        return status;
    }

    public void setStatus(CSIBookingStatus status) {
        this.status = status;
    }


}
