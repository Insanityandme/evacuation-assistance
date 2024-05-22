package com.evac.models;

import javax.persistence.*;

/**
 * This class is an entity tha  t creates a table in the database to link a user with a handicap.
 * It uses two columns as primary key (@IdClass).
 */
@Entity
@IdClass(UserHandicapRepositoryKey.class)
@Table(name = "user_handicap")

public class UserHandicap {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "handicap_id")
    private Long handicapId;

    public UserHandicap() {
    }

    public UserHandicap(Long userId, Long handicapId) {
        this.userId = userId;
        this.handicapId = handicapId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHandicapId() {
        return handicapId;
    }

    public void setHandicapId(Long handicapId) {
        this.handicapId = handicapId;
    }
}
