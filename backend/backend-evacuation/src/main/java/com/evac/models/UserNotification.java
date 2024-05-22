package com.evac.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * this class is an entity linking a userId with a
 * message and a name(of the message).
 */
@Entity
@Table( name = "user_notifications")


public class UserNotification {

    public UserNotification(){}

    public UserNotification(Long userId, String name, String message){
        this.userId = userId;
        this.message = message;
        this.name = name;
    }


    @NotNull
    private Long userId; //Name of the variable must be the same as the column name in the table


    @Id
    @NotBlank
    @Column(length = 20)
    private String name;

    @Column(length = 100)
    private String message;


    public Long getId() {
        return userId;
    }

    public void setId(Long id) {
        this.userId = id;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}