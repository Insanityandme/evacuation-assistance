package com.evac.payload.request;

import com.evac.models.Handicap;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * This class is a request to be sent with updated information of the user and his/her position
 */
@JsonSerialize
public class AllUserPosRequest {
    private  String handicap;
    private boolean needsHelp;
    @NotBlank
    private String username;

    private String position;

    @NotBlank
    private LocalDateTime localDateTime;


    private String floorName;


    private String zoneName;

    public AllUserPosRequest(String username, String sensorSetPos, LocalDateTime localDateTime, String floorName, String zoneName, boolean needsHelp, String handicap) {
        this.username = username;
        this.position = sensorSetPos;
        this.localDateTime = localDateTime;
        this.floorName = floorName;
        this.zoneName = zoneName;
        this.needsHelp = needsHelp;
        this.handicap = handicap;

    }
    public AllUserPosRequest() {

    }

    public AllUserPosRequest(String username, LocalDateTime localDateTime) {
        this.username = username;
        this.localDateTime = localDateTime;
    }

    public AllUserPosRequest(String username, LocalDateTime localDateTime, String handicap) {
        this.username = username;
        this.localDateTime = localDateTime;
        this.handicap = handicap;

    }

    public AllUserPosRequest(String username, String sensorSetPos, LocalDateTime localDateTime, String floorName, String zoneName, boolean needsHelp) {
        this.username = username;
        this.position = sensorSetPos;
        this.localDateTime = localDateTime;
        this.floorName = floorName;
        this.zoneName = zoneName;
        this.needsHelp = needsHelp;
    }

    public AllUserPosRequest(String username, LocalDateTime localDateTime, String handicapName, boolean needsHelp) {
        this.username = username;
        this.localDateTime = localDateTime;
        this.needsHelp = needsHelp;
    }

    public AllUserPosRequest(String username, LocalDateTime localDateTime, boolean needsHelp) {
        this.username = username;
        this.localDateTime = localDateTime;
        this.needsHelp = needsHelp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public boolean isNeedsHelp() {
        return needsHelp;
    }

    public String getHandicap() {
        return handicap;
    }
}
