package com.evac.payload.request;

import javax.validation.constraints.NotBlank;
/**
 * this is a class used for storing information
 * in a @RequestBody in NotifyController. It has
 * getters which are used to extract information from
 * the @RequestBody.
 */
public class DelegationDeleteRequest {

    public DelegationDeleteRequest() {
    }
    @NotBlank
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
