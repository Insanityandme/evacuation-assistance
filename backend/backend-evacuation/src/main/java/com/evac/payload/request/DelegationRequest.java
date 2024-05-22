package com.evac.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * This class is a request to delegate a zone and a floor to an evacuation leader
 */
public class DelegationRequest {

    private Set<String> zone;
    @NotBlank
    @Size(min = 6, max = 40)
    private String floorname;

    public Set<String> getZone() {
        return this.zone;
    }

    public void setZone(Set<String> zone) {
        this.zone = zone;
    }

    public String getFloorname() {
        return floorname;
    }

    public void setFloorname(String floorname) {
        this.floorname = floorname;
    }
}
