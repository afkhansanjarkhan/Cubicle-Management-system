package com.modeln.spaceit.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Table(name="deptcubicle")
public class CSIDeptCubicle extends CSIBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id",referencedColumnName = "departmentId")
    @JsonIgnoreProperties("deptCubicleList")
    private CSIDepartment department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cubicle_id",referencedColumnName = "id")
    @JsonIgnoreProperties("deptCubicleList")
    private CSICubicle cubicle;

    private Timestamp startDate;

    private Timestamp endDate;



    public CSICubicle getCubicle() {
        return cubicle;
    }

    public void setCubicle(CSICubicle cubicle) {
        this.cubicle = cubicle;
    }

    public CSIDepartment getDepartment() {
        return department;
    }

    public void setDepartment(CSIDepartment department) {
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


}
