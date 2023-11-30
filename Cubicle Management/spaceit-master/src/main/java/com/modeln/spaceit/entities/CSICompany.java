package com.modeln.spaceit.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","locationList","departmentList"})
@Table(name = "company")
public class CSICompany extends CSIBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long companyId;

    private String companyName;

    private String companyAddress;

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties("company")
    private List<CSILocation> locationList = new ArrayList<>();

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties("company")
    private List<CSIDepartment> departmentList = new ArrayList<>();



    public List<CSIDepartment> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<CSIDepartment> departmentList) {
        this.departmentList = departmentList;
    }

    public List<CSILocation> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<CSILocation> locationList) {
        this.locationList = locationList;
    }


    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }


}