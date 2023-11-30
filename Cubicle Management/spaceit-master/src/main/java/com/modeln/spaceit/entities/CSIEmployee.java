package com.modeln.spaceit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modeln.spaceit.enums.CSIRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","bookingList","occupancyList"})
@Table(name = "employee")
public class CSIEmployee extends CSIBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column( nullable = false)
    private Long employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id",referencedColumnName = "departmentId")
    @JsonIgnoreProperties("employeeList")
    private CSIDepartment department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id",referencedColumnName = "locationId")
    @JsonIgnoreProperties("employeeList")
    private CSILocation location;

    @Column(nullable = false)
    private String employeeName;

    @Column(nullable = false)
    private String emailId;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private CSIRole role;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties("employee")
    private List<CSIBooking> bookingList= new ArrayList<>();

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties("employee")
    private List<CSIOccupancy> occupancyList = new ArrayList<>();

    public List<CSIOccupancy> getOccupancyList() {
        return occupancyList;
    }

    public void setOccupancyList(List<CSIOccupancy> occupancyList) {
        this.occupancyList = occupancyList;
    }

    public CSIDepartment getDepartment() {
        return department;
    }

    public void setDepartment(CSIDepartment department) {
        this.department = department;
    }

    public CSILocation getLocation() {
        return location;
    }

    public void setLocation(CSILocation location) {
        this.location = location;
    }

    public List<CSIBooking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<CSIBooking> bookingList) {
        this.bookingList = bookingList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public CSIRole getRole() {
        return role;
    }

    public void setRole(CSIRole role) {
        this.role = role;
    }
}