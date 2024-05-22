package com.evac.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * this class is an entity that creates a table in the database
 * storing usernames of deputy leaders and a boolean isActive.
 */
@Entity
@Table(name = "evac_active")
public class EvacActive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    private boolean isActive = false;

    public EvacActive() {}

    public EvacActive(String username) {
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

    public void setActiveTrue() {
        if(!isActive){
            isActive = true;
        }
    }
    public void setActiveFalse() {
        if(isActive){
            isActive = false;
        }
    }
}