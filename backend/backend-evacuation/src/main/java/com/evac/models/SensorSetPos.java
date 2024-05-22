package com.evac.models;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This entity creates a table that will give details to the sensors (floorName, zoneName, position, id)
 */
@Entity
@Table(name = "sensorSetPos")
public class SensorSetPos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 20)
    private String floorName;

    @Column(length = 5)
    private String zoneName;

    @Column(name = "position")
    private String position;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SensorSetPos() {
    }

    public SensorSetPos(String position, String zoneName, String floorName) {
        this.position = position;
        this.zoneName = zoneName;
        this.floorName = floorName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getZoneName() {
        return zoneName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }
}
