package com.evac.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * this class is an entity that creates a table in the database
 * for storing names of floors with a generated id.
 */
@Entity
@Table(name = "floors")
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 20)
    private String name;

    public Floor() {}

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
