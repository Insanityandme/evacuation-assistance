package com.evac.models;

import javax.persistence.Entity;

public class Alarm {
    private boolean isActivated = false;

    public Alarm(){

    }

    public void activate(){
        isActivated = true;
    }
    public void deactivate(){
        isActivated = false;
    }

    public boolean isActivated() {
        return isActivated;
    }


}
