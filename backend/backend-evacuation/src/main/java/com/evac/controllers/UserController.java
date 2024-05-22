package com.evac.controllers;

import com.evac.models.Handicap;
import com.evac.models.UserHandicap;
import com.evac.repository.UserHandicapRepository;
import com.evac.repository.HandicapRepository;
import com.evac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is a RestController that will manage HTTP requests from the user to the server
 * UserController is used for requests related to user details
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/userAuth")
public class UserController {

    //Oversee if [all] these variables should be set to private!
    @Autowired
    private HandicapRepository handicapRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHandicapRepository userHandicapRepository;


    /**
     * This method is a request to add a new type of handicap to the database
     *
     * @param newHandicap --> new type of handicap to be added
     * @return an ok-response in case the handicap has been added to the database
     */
    @PostMapping("/addHandicap")
    public ResponseEntity<?> addHandicap(@RequestBody Handicap newHandicap) {
        Handicap handicap = new Handicap(newHandicap.getName());

        handicapRepository.save(handicap);

        return ResponseEntity.ok("Handicap added successfully");

    }

    /**
     * This method is a request to get all the handicaps registered in the database
     *
     * @return all the handicaps
     */
    @GetMapping("/getAllHandicaps")
    public List<Handicap> getAllHandicaps() {
        return handicapRepository.findAll();
    }

    /**
     * This method is a request to set a handicap to a user by using the user's id
     *
     * @param userId   --> the id of the user to set the handicap on
     * @param handicap --> handicap as a json body request
     * @return an ok-response in case the handicap has been set to the user or a bad request in case the handicap or the user doesn't exist
     */
    @PostMapping("/setHandicapToUser/{userId}")
    public ResponseEntity<?> setHandicapToUser(@PathVariable("userId") Long userId, @RequestBody Handicap handicap) {
        if (userRepository.existsById(userId)) {
            if (handicapRepository.existsById(handicap.getId())) {
                UserHandicap newUserHandicap = new UserHandicap(userId, handicap.getId());
                userHandicapRepository.save(newUserHandicap);

                return ResponseEntity.ok("Handicap added to user successfully!");
            } else {
                return ResponseEntity
                        .badRequest()
                        .body("No handicap with this id exist!");
            }
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("No user with this id exist!");
        }
    }

    /**
     * This method is a request to get all the user with a handicap in the database
     *
     * @return all the users with a handicap
     */
    @GetMapping("/getUsersWithHandicaps")
    public List<UserHandicap> getUsersWithHandicaps() {
        return userHandicapRepository.findAll();
    }

}
