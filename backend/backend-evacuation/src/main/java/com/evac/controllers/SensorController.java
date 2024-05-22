package com.evac.controllers;

import com.evac.models.*;
import com.evac.payload.request.AllSensorRequest;
import com.evac.payload.request.AllUserPosRequest;
import com.evac.payload.request.UserSensorPosRequest;
import com.evac.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.evac.payload.request.SensorRequest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sensor")
public class SensorController {
    @Autowired
    private SensorSetPosRepository sensorSetPosRepository;
    @Autowired
    private SensorSetRepository sensorSetRepository;
    @Autowired
    private UserSensorPosRepository userSensorPosRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserHandicapRepository userHandicapRepository;
    @Autowired
    private HandicapRepository handicapRepository;

    /**
     * This method adds a sensor into the database by creating a SensorRequest object with the necessary data
     * @param sensorRequest -> Object that contains important data (sensorName, position, floorName, zoneName)
     * @return -> ResponseEntity to check if the request was a success or not
     */
    @PostMapping("/addSensor")
    public ResponseEntity<?> addSensor(@RequestBody SensorRequest sensorRequest) {

        Set<String> strSensorNames = sensorRequest.getSensorName();
        System.out.println(strSensorNames);
        String position = sensorRequest.getPosition();
        String floorName = sensorRequest.getFloorName();
        String zoneName = sensorRequest.getZoneName();
        System.out.println(position);
        SensorSetPos sensorSetPos = new SensorSetPos(position, zoneName, floorName);
        sensorSetPosRepository.save(sensorSetPos);

        for (String sensorName : strSensorNames) {
            SensorSet sensorSet = new SensorSet(sensorSetPos, sensorName);
            sensorSetRepository.save(sensorSet);
        }
        return ResponseEntity.ok("goodie");
    }

    /**
     * This method gets all the sensors available in the database
     * @return -> an AllSensorRequest object with all the information of the sensor to be returned
     */
    @GetMapping("/getAllSensors")
    public List<AllSensorRequest> getAllSensors() {
        List<SensorSet> sensorSets = sensorSetRepository.findAll();
        List<AllSensorRequest> allSensorRequests = new ArrayList<>();
        for (SensorSet set : sensorSets) {
            String name = set.getSensorName();
            SensorSetPos sensorSetPos = set.getSensorSetPos();
            Long id = sensorSetPos.getId();
            String position = sensorSetPos.getPosition();
            String floorName = sensorSetPos.getFloorName();
            String zoneName = sensorSetPos.getZoneName();
            AllSensorRequest allSensorRequest = new AllSensorRequest(
                    name, id, position, floorName, zoneName
            );
            allSensorRequests.add(allSensorRequest);


        }
        return allSensorRequests;
    }

    /**
     * updates userSensorPos table with
     * username, Id(of a sensor-set), and a position of the sensorset with given id.
     * Deletes previous entry in UserSensorPos table and writes new entry
     *
     * @param userSensorPosRequest -> object with the updated user position
     * @return -> ResponseEntity to check if the request was successful or not
     */

    @PostMapping("/updateUserPos")
    @Transactional
    public ResponseEntity<?> updateUserPos(@RequestBody UserSensorPosRequest userSensorPosRequest) {
        String username = userSensorPosRequest.getUsername();
        Long sensorSetId = userSensorPosRequest.getId();
        SensorSetPos sensorSetPos = sensorSetPosRepository.findById(sensorSetId).get();
        String position = sensorSetPos.getPosition();
        boolean needsHelp = false;

        if (userSensorPosRepository.existsByUsername(username)) {
            UserSensorPos userSensorPos = userSensorPosRepository.findByUsername(username).get();
            userSensorPos.setSensorSetPos(position);
            userSensorPos.setLocalDateTime(LocalDateTime.now());
            userSensorPosRepository.findByUsername(username)
                    .map(newUser -> this.userSensorPosRepository.save(userSensorPos));
        }


        return ResponseEntity.ok("users position updated");
    }

    /**
     * This method updates th default user position of a user
     * @param userSensorPosRequest -> Object that will contain the updated default position.
     * @return ResponseEntity to check if the request was successful or not
     */
    @PostMapping("/updateDefaultUserPos")
    @Transactional
    public ResponseEntity<?> updateDefaultUserPos(@RequestBody UserSensorPosRequest userSensorPosRequest) {
        String username = userSensorPosRequest.getUsername();
        LocalDateTime localDateTime = LocalDateTime.now();

        if (userSensorPosRepository.existsByUsername(username)) {
            userSensorPosRepository.deleteByUsername(username);
        }
        UserSensorPos userSensorPos = new UserSensorPos(localDateTime, username);
        userSensorPosRepository.save(userSensorPos);
        return ResponseEntity.ok(username + " added to userSensorPos without position");
    }

    /**
     * This method sets all the user positions to their default positions
     * @return -> ResponseEntity to check if the request was a success or not
     */
    @PostMapping("/allDefaultUserPos")
    @Transactional
    public ResponseEntity<?> allDefaultUserPos() {
        List<UserSensorPos> userSensorPos = userSensorPosRepository.findAll();
        for (UserSensorPos user : userSensorPos
             ) {
            user.setSensorSetPos(null);
            user.setNeedsHelpFalse();
            user.setLocalDateTime(LocalDateTime.now());
            String username = user.getUsername();
            userSensorPosRepository.findByUsername(username)
                    .map(newUser -> this.userSensorPosRepository.save(user));

        }
        return ResponseEntity.ok("all users set to default position");
    }

    /**
     * This method sets the status of help of a user to false using his/her username
     * @param username -> Username of the user to be updated
     * @return -> A UserSensorPos object with the updated status or null in case there is not a user with given username
     */
    @PostMapping("/updateNeedsHelpFalse/{username}")
    public UserSensorPos updateNeedsHelpFalse(@PathVariable("username") String username) {
        if (userSensorPosRepository.existsByUsername(username)) {
            UserSensorPos user = userSensorPosRepository.findByUsername(username).get();
            user.setNeedsHelpFalse();
            userSensorPosRepository.findByUsername(username)
                    .map(newUser -> this.userSensorPosRepository.save(user));
            return user;
        }
        return null;
    }

    /**
     * This method updates the status of help of a user to true by using his/her username
     * @param username -> the username of the user to be updated
     * @return -> UserSensorPos object with the updated status or null in case there is no user with given username
     */
    @PostMapping("/updateNeedsHelpTrue/{username}")
    public UserSensorPos updateNeedsHelpTrue(@PathVariable("username") String username) {
        if (userSensorPosRepository.existsByUsername(username)) {
            UserSensorPos user = userSensorPosRepository.findByUsername(username).get();
            user.setNeedsHelpTrue();
            user.setLocalDateTime(LocalDateTime.now());
            userSensorPosRepository.findByUsername(username)
                    .map(newUser -> this.userSensorPosRepository.save(user));
            return user;
        }
        return null;
    }


    /**
     * This method gets the user position by using his/her username
     * @param username -> Username of the user to get his/her position
     * @return -> ResponseEntity to check if the request was successful or not
     */
    @GetMapping("/getUserPos/{username}")
    public ResponseEntity<?> getUserPos
            (@PathVariable("username") String username) {
        System.out.println(username);

        if (userSensorPosRepository.existsByUsername(username)) {
            UserSensorPos userr = userSensorPosRepository.findByUsername(username).get();
            return ResponseEntity.ok(userr);
        }
        return ResponseEntity.badRequest().body("username not found in userSensorPos");
    }

    /**
     * This method gets all the positions of all the users
     * @return -> All the positions for all the users
     */
    @GetMapping("/getAllUserPos")
    public List<AllUserPosRequest> getAllUserPos() {
        List<UserSensorPos> userSensorPosList = userSensorPosRepository.findAll();
        List<AllUserPosRequest> userPosRequests = new ArrayList<>();

        for (UserSensorPos userSensorPos : userSensorPosList) {
            boolean needsHelp = userSensorPos.getNeedshelp();

            LocalDateTime localDateTime = userSensorPos.getLocalDateTime();
            String username = userSensorPos.getUsername();
            User user = userRepository.findByUsername(username).get();
            long id = user.getId();
            UserHandicap userHandicap;
            Long handicapId;
            Handicap handicap = null;
            String handicapName = null;

            if (userHandicapRepository.existsByuserId(id)) {
                userHandicap = userHandicapRepository.findByuserId(id).get();
                handicapId = userHandicap.getHandicapId();
                handicap = handicapRepository.findById(handicapId).get();
                handicapName = handicap.getName();
            }

            String sensorSetPos = userSensorPos.getSensorSetPos();
            if ((sensorSetPos != null) && (handicap != null)) {
                SensorSetPos setPos = sensorSetPosRepository.findByPosition(sensorSetPos).get();
                String floorName = setPos.getFloorName();
                String zoneName = setPos.getZoneName();

                AllUserPosRequest request = new AllUserPosRequest(
                        username, sensorSetPos, localDateTime, floorName, zoneName, needsHelp, handicapName);
                userPosRequests.add(request);

            } else if ((sensorSetPos == null) && (handicap != null)) {
                AllUserPosRequest request = new AllUserPosRequest(
                        username, localDateTime, handicapName, needsHelp);
                userPosRequests.add(request);
            } else if ((sensorSetPos == null) && (handicap == null)) {
                AllUserPosRequest request = new AllUserPosRequest(
                        username, localDateTime, needsHelp);
                userPosRequests.add(request);

            } else if ((sensorSetPos != null) && (handicap == null)) {
                SensorSetPos setPos = sensorSetPosRepository.findByPosition(sensorSetPos).get();
                String floorName = setPos.getFloorName();
                String zoneName = setPos.getZoneName();

                AllUserPosRequest request = new AllUserPosRequest(
                        username, sensorSetPos, localDateTime, floorName, zoneName, needsHelp);
                userPosRequests.add(request);
            }
        }

        return userPosRequests;
    }


}
