package com.evac.payload.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * This class is a request to be sent with updated information about the sensor
 */
@JsonSerialize
public class AllSensorRequest {

    private String sensorName;

    private Long id;

    private String position;

    private String floorName;

    private String zoneName;

    public AllSensorRequest(String sensorName, Long id, String position, String floorName, String zoneName) {
        this.sensorName = sensorName;
        this.id = id;
        this.position = position;
        this.floorName = floorName;
        this.zoneName = zoneName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public String getFloorName() {
        return floorName;
    }

    public String getPosition() {
        return position;
    }

    public String getSensorName() {
        return sensorName;
    }

    public Long getId() {
        return id;
    }
}
