package com.evac.models;

import java.io.Serializable;

/**
 * this class is used to allow UserHandicap table to have two columns as primary key
 */
public class UserHandicapRepositoryKey implements Serializable {
    private Long userId;
    private Long handicapId;

}
