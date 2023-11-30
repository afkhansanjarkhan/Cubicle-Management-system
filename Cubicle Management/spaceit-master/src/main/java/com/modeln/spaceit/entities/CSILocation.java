package com.modeln.spaceit.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","employeeList","cubicleList"})
@Table(name = "location")
public class CSILocation extends CSIBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column(nullable = false)
    private String locationName;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",referencedColumnName = "companyId")
    @JsonIgnoreProperties("locationList")
    private CSICompany company;

    @OneToMany(mappedBy = "location",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties("location")
    private List<CSICubicle> cubicleList= new ArrayList<>();

    @OneToMany(mappedBy = "location",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties("location")
    private List<CSIEmployee> employeeList = new ArrayList<>();



    public List<CSIEmployee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<CSIEmployee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<CSICubicle> getCubicleList() {
        return cubicleList;
    }

    public void setCubicleList(List<CSICubicle> cubicleList) {
        this.cubicleList = cubicleList;
    }

    public CSICompany getCompany() {
        return company;
    }

    public void setCompany(CSICompany company) {
        this.company = company;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}