package com.evac.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * this class is an entity that creates a table in the database
 * storing usernames of deputy leaders and a boolean isActive.
 */
@Entity
@Table(name = "deputies")
public class Deputy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    private boolean isActive = false;

    public Deputy() {}

    public Deputy(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive() {
        if(!isActive){
            isActive = true;
        } else {
            isActive = false;
        }
    }
}
