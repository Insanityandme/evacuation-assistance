package com.evac.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * This class is an entity that creates a table
 * in the database for all the priority levels
 */
@Entity
@Table(name = "Priority",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")
        })
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

