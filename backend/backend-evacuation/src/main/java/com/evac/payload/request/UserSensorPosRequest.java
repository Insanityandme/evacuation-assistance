package com.evac.payload.request;

import javax.validation.constraints.NotBlank;

/**
 * This class is a request to add an updated id for the sensor-set and the username of the user.
 */
public class UserSensorPosRequest {

    @NotBlank
    private long id;

    @NotBlank
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
