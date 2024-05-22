package com.evac.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * this class is an entity creating a table for storing
 * the names of zones with a generated Id.
 */
@Entity
@Table(name = "zones")
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 1)
    private String name;

    public Zone() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
