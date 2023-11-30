package com.modeln.spaceit.entities;

import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modeln.spaceit.enums.CSICubicleStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","deptCubicleList","bookingList"})
@Table(name="cubicle")
public class CSICubicle extends CSIBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cubicleId;

    @Enumerated(EnumType.STRING)
    private CSICubicleStatus Status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id",referencedColumnName = "locationId")
    @JsonIgnoreProperties("cubicleList")
    private CSILocation location;

    @OneToMany(mappedBy = "cubicle",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties("cubicle")
    private List<CSIDeptCubicle> deptCubicleList = new ArrayList<>();

    @OneToMany(mappedBy = "cubicle",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties("cubicle")
    private List<CSIBooking> bookingList = new ArrayList<>();



    public List<CSIBooking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<CSIBooking> bookingList) {
        this.bookingList = bookingList;
    }

    public CSILocation getLocation() {
        return location;
    }

    public void setLocation(CSILocation location) {
        this.location = location;
    }

    public List<CSIDeptCubicle> getDeptCubicleList() {
        return deptCubicleList;
    }

    public void setDeptCubicleList(List<CSIDeptCubicle> deptCubicleList) {
        this.deptCubicleList = deptCubicleList;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCubicleId() {
        return cubicleId;
    }

    public void setCubicleId(String cubicleId) {
        this.cubicleId = cubicleId;
    }

    public CSICubicleStatus getStatus() {
        return Status;
    }

    public void setStatus(CSICubicleStatus status) {
        Status = status;
    }

}
