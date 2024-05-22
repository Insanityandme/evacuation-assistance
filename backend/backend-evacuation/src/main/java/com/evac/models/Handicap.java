package com.evac.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This class is an entity that creates a table in the database
 * with all the handicaps
 */
@Entity
@Table(name = "handicap",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
                @UniqueConstraint(columnNames = "name")
        })
public class Handicap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;


    public Handicap() {
    }

    public Handicap(String name) {
        this.name = name;
    }


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
