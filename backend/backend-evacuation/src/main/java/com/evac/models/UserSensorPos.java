package com.evac.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * This entity creates a table that will help us track the users and now where they need help in case they do
 */
@Entity
@IdClass(UserSensorPosRepostiroyKey.class)
@Table(name = "userSensorPos")
public class UserSensorPos {

    @Id
    @Column
    private String username;


    @Column
    private String sensorSetPos;

    @Column
    private LocalDateTime localDateTime;

    @Column
    private boolean needsHelp = false;

    public UserSensorPos(String sensorSetPos, LocalDateTime localDateTime, String username){
        this.sensorSetPos = sensorSetPos;
        this.localDateTime = localDateTime;
        this.username = username;
    }

    public UserSensorPos() {

    }

    public UserSensorPos(LocalDateTime localDateTime, String username) {
        this.localDateTime = localDateTime;
        this.username = username;
    }

    public UserSensorPos(LocalDateTime localDateTime, String username, boolean needsHelp) {
        this.localDateTime = localDateTime;
        this.username = username;
        this.needsHelp = needsHelp;
    }

    public UserSensorPos(String sensorSetPos, LocalDateTime localDateTime, String username, boolean needsHelp) {
        this.sensorSetPos = sensorSetPos;
        this.localDateTime = localDateTime;
        this.username = username;
        this.needsHelp = needsHelp;
    }

    public String getSensorSetPos() {
        return sensorSetPos;
    }

    public void setSensorSetPos(String sensorSetPos) {
        this.sensorSetPos = sensorSetPos;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNeedsHelpTrue() {
        if(!needsHelp){
            needsHelp = true;
        }
    }
    public void setNeedsHelpFalse() {
        if(needsHelp){
            needsHelp = false;
        }
    }
    public boolean getNeedshelp() {
        return needsHelp;
    }
}
