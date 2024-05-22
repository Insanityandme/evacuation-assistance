package com.evac.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;

/**
 * This class is an entity that creates a table in the database. This table will contain a registration token and the email
 * of the user that owns it.
 */
@Entity
@Table(name = "Token",
uniqueConstraints ={
        @UniqueConstraint(columnNames = "token"),
        @UniqueConstraint(columnNames = "email")
})
public class Token {

    @Id
    private String token;

    @Email
    private String email;

    public Token(){

    }

    @JsonCreator
    public Token(@JsonProperty("token") String token, String email){
        this.token = token;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_email() {
        return email;
    }

    public void setUser_email(String email) {
        this.email = email;
    }
}
