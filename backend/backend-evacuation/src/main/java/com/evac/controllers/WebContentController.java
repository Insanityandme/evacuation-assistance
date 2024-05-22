package com.evac.controllers;

import com.evac.models.Alarm;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/alarm")
public class WebContentController {

    private Alarm alarm = new Alarm();
    @PostMapping("/activate")
    public Alarm activateStatus(){
        alarm.activate();

        return alarm;
    }

    @PostMapping("/deactivate")
    public Alarm deactivateStatus(){
        alarm.deactivate();

        return alarm;
    }

    @GetMapping(value = "/status",produces = "application/json")
    public Alarm getStatus(){
        if (alarm.isActivated() == true) {
            return alarm;
        } else {
            return alarm;
            //return ResponseEntity.ok().body(alarm.isActivated());
        }
    }




}
