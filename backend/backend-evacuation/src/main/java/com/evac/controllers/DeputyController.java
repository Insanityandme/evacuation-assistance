package com.evac.controllers;
import java.util.*;
import com.evac.models.*;
import com.evac.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * this class is a RestController is responsible for managing the HTTP requests sent by users
 * to the server wanting to change the active-status on a deputyleader.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/deputyAuth")

public class DeputyController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    DeputyRepository deputyRepository;

    /**
     * this Mapping is responsible for handling a request to change the active-status of
     * a deputyleader.
     * It checks if there is another deputyleader who already has active-status: active,
     * and gives a badRequest response if so.
     *
     * @param username of the deputleader who is to have the status changed.
     * @return an ok or badRequest response.
     */
    @PutMapping("/changeActive/{username}")
    public ResponseEntity<?> changeActive(@PathVariable("username") String username){
        Optional <User> user = userRepository.findByUsername(username);
        List<Deputy> deputylist = deputyRepository.findAll();

        for(Deputy deputy: deputylist) {
            if(deputy.isActive() && !deputy.getUsername().equals(username)){
                return ResponseEntity
                        .badRequest()
                        .body("Active deputy already assigned");
            }
        }

        for (Role role : user.get().getRoles()) {
            if (role.getName().equals(ERole.ROLE_DEPUTYLEADER)) {
                Optional <Deputy> deputy = deputyRepository.findByUsername(username);
                Deputy newDeputy = deputy.get();
                newDeputy.setActive();
                deputyRepository.findByUsername(username)
                        .map(updatedDeputy -> this.deputyRepository.save(newDeputy));

                return ResponseEntity.ok("deputyleader activity status changed: " + deputy.get().isActive());
            }
        }

        return ResponseEntity
                .badRequest()
                .body("Invalid role");
    }
}
