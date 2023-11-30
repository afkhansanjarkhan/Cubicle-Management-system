package com.modeln.spaceit.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modeln.spaceit.enums.CSINotifyObj;
import com.modeln.spaceit.enums.CSINotifyType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "notification")
public class CSINotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private boolean read;

    @Enumerated(EnumType.STRING)
    private CSINotifyType notifyType;

    @Enumerated(EnumType.STRING)
    private CSINotifyObj notifyObj;

    @CreationTimestamp
    @Column(updatable = false)
    Timestamp dateCreated;

    @UpdateTimestamp
    Timestamp lastModified;

    private Long obj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id",referencedColumnName = "employeeId" ,insertable=false, updatable=false)
    @JsonIgnoreProperties("notificationsList")
    private  CSIEmployee targetUser;

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public CSIEmployee getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(CSIEmployee targetUser) {
        this.targetUser = targetUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public CSINotifyType getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(CSINotifyType notifyType) {
        this.notifyType = notifyType;
    }

    public CSINotifyObj getNotifyObj() {
        return notifyObj;
    }

    public void setNotifyObj(CSINotifyObj notifyObj) {
        this.notifyObj = notifyObj;
    }

    public Long getObj() {
        return obj;
    }

    public void setObj(Long obj) {
        this.obj = obj;
    }
}

