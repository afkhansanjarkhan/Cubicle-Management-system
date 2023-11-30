package com.modeln.spaceit.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Table( name = "occupancy")
public class CSIOccupancy extends CSIBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id",referencedColumnName = "id")
    @JsonIgnoreProperties("occupancyList")
    private CSIEmployee employee;

    private Timestamp loginTime;

    private Timestamp logoutTime;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CSIEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(CSIEmployee employee) {
        this.employee = employee;
    }



    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }




}
