package com.evac.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * this class is an entity that creates a table in the database to link
 * a users id, username with a floor and zone.
 */
@Entity
@Table( name = "delegations")


public class Delegation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @Column(length = 20)
    private String floorName;

    @Column(length = 5)
    private String zoneName;


    public Delegation(String username, Long id) {
        this.username = username;
        this.id = id;
    }

    public Delegation(String username, String floorName, String zoneName) {
        this.username = username;
        this.floorName = floorName;
        this.zoneName = zoneName;
    }

    public Delegation() {}

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String name) {
        this.zoneName = name;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Long getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
