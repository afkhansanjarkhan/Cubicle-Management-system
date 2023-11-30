package com.modeln.spaceit.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","employeeList","deptCubicleList"})
@Table(name = "department")
public class CSIDepartment extends CSIBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;



    @Column(nullable = false)
    private  String departmentName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",referencedColumnName = "companyId")
    @JsonIgnoreProperties("departmentList")
    private CSICompany company;

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties("department")
    private List<CSIDeptCubicle> deptCubicleList = new ArrayList<>();

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties("department")
    private List<CSIEmployee> employeeList= new ArrayList<>();

    public List<CSIEmployee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<CSIEmployee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<CSIDeptCubicle> getDeptCubicleList() {
        return deptCubicleList;
    }

    public void setDeptCubicleList(List<CSIDeptCubicle> deptCubicleList) {
        this.deptCubicleList = deptCubicleList;
    }

    public CSICompany getCompany() {
        return company;
    }

    public void setCompany(CSICompany company) {
        this.company = company;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }



}