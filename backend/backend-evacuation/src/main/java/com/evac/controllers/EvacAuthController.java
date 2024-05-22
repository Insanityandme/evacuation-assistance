package com.evac.controllers;

import java.util.*;

import com.evac.models.*;
import com.evac.payload.request.DelegationDeleteRequest;
import com.evac.payload.request.DelegationRequest;
import com.evac.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.evac.security.jwt.JwtUtils;

import javax.transaction.Transactional;

/**
 * this class is a RestController responsible for managing the HTTP requests sent by users
 * for managing delegations of floor/zones and setting priorities to evacuation-leaders.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/evacAuth")
public class EvacAuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PriorityRepository priorityRepository;
    @Autowired
    EvacLeaderPriorityRepository evacLeaderPriorityRepository;

    @Autowired
    FloorRepository floorRepository;

    @Autowired
    ZoneRepository zoneRepository;
    @Autowired
    DelegationRepository delegationRepository;
    @Autowired
    private EvacActiveRepository evacActiveRepository;

    /**
     * this mapping is responsible for handling a request to put a row consisting of
     * id, username, floorname, zonename into the delegations table.
     * @param userId the id of a user which username is to be added to delgation table
     * @param delegationRequest is a class that contains information(floor, zones)
     *                          to be added to the delegation table.
     * @return an ok response if the row was added to the table
     * a badrequest response if the role with the userid is not evacleader,
     * if the evacleader with the id is already in the table, or if
     * there is no evacleader matching the id.
     */
    @PostMapping("/delegateById/{userId}")
    @Transactional
    public ResponseEntity<?> addDelegate(@PathVariable("userId") Long userId,
                                         @RequestBody DelegationRequest delegationRequest) {

        Optional<User> user;
        Set<String> strZones = delegationRequest.getZone();
        System.out.println(strZones);
        String floorName = delegationRequest.getFloorname();
        for(String zone : strZones) {
            if(!(floorRepository.existsByName(floorName) && zoneRepository.existsByName(zone))) {
                return ResponseEntity.badRequest().body("invalid floor or zone-name");
            }
        }
        user = userRepository.findById(userId);
        String username = user.get().getUsername();

        if (userRepository.existsById(userId)) {
            if (!delegationRepository.existsByUsername(username)) {

                for (Role role : user.get().getRoles()) {
                    if (role.getName().equals(ERole.ROLE_EVACLEADER)) {

                        System.out.println("hej");
                        for (String zone : strZones) {

                            if (zoneRepository.existsByName(zone)) {
                                Delegation delegation = new Delegation(
                                        user.get().getUsername(), floorName, zone);
                                delegationRepository.save(delegation);

                            }

                        }
                        return ResponseEntity.ok(user.get().getUsername() + " delegated to floor : " +
                                floorName + " and zones: " + strZones);

                    }
                }

                return ResponseEntity
                        .badRequest()
                        .body("Invalid role");
            } else {
                delegationRepository.deleteByUsername(username);
                for (Role role : user.get().getRoles()) {
                    if (role.getName().equals(ERole.ROLE_EVACLEADER)) {

                        System.out.println("hej");
                        for (String zone : strZones) {

                            if (zoneRepository.existsByName(zone)) {
                                Delegation delegation = new Delegation(
                                        user.get().getUsername(), floorName, zone);
                                delegationRepository.save(delegation);

                            }

                        }
                        return ResponseEntity.ok(user.get().getUsername() + " delegated to floor : " +
                                floorName + " and zones: " + strZones);

                    }
                }

                return ResponseEntity
                        .badRequest()
                        .body("Invalid role");
            }
        } else {
            user = null;
            return ResponseEntity
                    .badRequest()
                    .body("No evacuation leader matching the id");
        }
    }

    /**
     * this mapping is responsible for handling a request to delete rows from the
     * delegations table with the username given in the request.
     * The method checks if there is one or several delegations with the given username
     * , and if there is it deletes the row or rows.
     * @param delegationDeleteRequest a class that gets a username from the requestbody,
     *                                with a getUsername() method that returns a username
     *                                to be deleted.
     * @return ok response if there is a user in the table with given username.
     * badRequest if there is not a user with the given username
     */
    @DeleteMapping("deleteDelegationByUsername")
    public ResponseEntity<?> deleteDelegationByUsername(@RequestBody DelegationDeleteRequest delegationDeleteRequest) {
        if (delegationRepository.existsByUsername(delegationDeleteRequest.getUsername())) {
            List <Delegation> delegationList = delegationRepository.findAll();

            for (Delegation delegation1 : delegationList) {
                System.out.println(delegation1.getUsername());
                System.out.println(delegationDeleteRequest.getUsername());

                if(delegation1.getUsername().equals(delegationDeleteRequest.getUsername())) {
                    Long id = delegation1.getId();
                    System.out.println(id);
                    delegationRepository.deleteById(id);
                 }

            }
            return ResponseEntity.ok("Delegation of floor/zones for leader succesfully deleted");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("No leader with given id delegated");
        }
    }

    /**
     * this mapping sets a priority chosen in the requestbody to a leader with an
     * id specified in the pathvariable, if an evacuationleader exists with the given id,
     * and a priority exists with the given priority.
     * @param leaderId the id of a evacuationleader in user table, to be added to
     *                 evacleader_priority table.
     *
     * @param evacLeaderPriority the id of a priority in the priority table,
     *                           to be assigned to the evacleader_priority table.
     * @return ok response if row added to evacleader_priority.
     * badRequest if no evacuation leader with id provided,
     * the id for priority is invalid, or the role of the user with given id
     * is not evacuation leader.
     **/

    @PostMapping("/setPriorityToEvacuationLeader/{leaderId}")

    public ResponseEntity<?> setPriority(@PathVariable Long leaderId, @RequestBody EvacLeaderPriority evacLeaderPriority) {
        Optional<User> user = null; // See if not initializing this variable also works. If so, try to apply samme way with other similar variables.

        if (!(userRepository.existsById(leaderId))) {
            return ResponseEntity
                    .badRequest()
                    .body("No evacuation leader with the id provided!");
        } else {

            if (!(priorityRepository.existsById(evacLeaderPriority.getpriority()))) {
                return ResponseEntity
                        .badRequest()
                        .body("Invalid priority!");
            } else {
                user = userRepository.findById(leaderId);
                if (!evacLeaderPriorityRepository.existsById(leaderId)) {
                    for (Role role : user.get().getRoles()) {

                        if (role.getName().equals(ERole.ROLE_EVACLEADER)) {
                            EvacLeaderPriority leaderPriority = new EvacLeaderPriority(leaderId, evacLeaderPriority.getpriority());
                            this.evacLeaderPriorityRepository.save(leaderPriority);

                            return ResponseEntity.ok("Priority set to evacuation leader!");
                        }
                    }

                    return ResponseEntity
                            .badRequest()
                            .body("Invalid role");

                } else {
                    evacLeaderPriorityRepository.deleteById(leaderId);
                    for (Role role : user.get().getRoles()) {

                        if (role.getName().equals(ERole.ROLE_EVACLEADER)) {
                            EvacLeaderPriority leaderPriority = new EvacLeaderPriority(leaderId, evacLeaderPriority.getpriority());
                            this.evacLeaderPriorityRepository.save(leaderPriority);

                            return ResponseEntity.ok("Priority set to evacuation leader!");
                        }
                        return ResponseEntity
                                .badRequest()
                                .body("Invalid role");
                    }
                }


            }
            return ResponseEntity.badRequest().body("no evacleader with given id");
        }

    }

    /**
     * this mapping is used to return all the rows in the priority table
     * @return all saved priorities in priorityrepository
     */

    @GetMapping("/getAllPriorities")
    public List<Priority> getAllPriorities() {
        return priorityRepository.findAll();
    }

    /**
     * deletes a row from priorities table with a given priorityId
     * @param priorityId id of the row to be deleted
     * @return ResponseEntity: ok if deleted succesfully, badrequest if no row with given id.
     */
    @DeleteMapping("deletePriorityById/{priorityId}")
    public ResponseEntity<?> deletePriorityById(@PathVariable("priorityId") Long priorityId) {
        if (priorityRepository.existsById(priorityId)) {
            priorityRepository.deleteById(priorityId);

            return ResponseEntity.ok("Priority deleted successfully!");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("No priority with the given id!");
        }

    }

    /**
     * this mapping is used to remove a row from evacleader_priority
     * with a given leaderId
     * @param leaderId the id of the leader on a row to be removed
     * @return ResponseEntity.ok response if removed succesfully
     * ResponseEntity.badRequest if no leader with given id.
     */
    @DeleteMapping("deleteLeaderAndPriorityById/{leaderId}")
    public ResponseEntity<?> deleteLeaderAndPriorityById(@PathVariable("leaderId") Long leaderId) {
        if (evacLeaderPriorityRepository.existsById(leaderId)) {
            evacLeaderPriorityRepository.deleteById(leaderId);

            return ResponseEntity.ok("Leader with his/her priority successfully deleted!");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("No leader with given id!");
        }
    }

    /**
     * this mapping is used to return a list of all rows in the
     * evacleader_priotity table, showing all evacuationleaders
     * with priorities set, and which priority is set.
     * @return List of all rows of evacleader_priority table
     */

    @GetMapping("getAllLeadersAndPriorities")
    public List<EvacLeaderPriority> getAllLeadersAndPriorities() {
        return evacLeaderPriorityRepository.findAll();
    }

    /**
     * This mapping is used to get a list of all delegations (id, username, zone, floor)
     * @return list of all rows of delegations table.
     */
    @GetMapping("getAllDelegations")
    public List<Delegation> getAllDelegations() {
        return delegationRepository.findAll();
    }

    /**
     * this mapping is used to get Delegation instances for a certain username.
     * @param username
     * @return LinkedList of all rows with specified username in it.
     */

    @GetMapping("/getDelegationsByUsername/{username}")
    public ResponseEntity<?> getDelegationByUsername(@PathVariable("username") String username) {
        List<Delegation> delegationsList = delegationRepository.findAll();
        List<Delegation> returnToFront = new LinkedList<Delegation>() {
        };
        for(Delegation delegation : delegationsList) {
            if(delegation.getUsername().equals(username)) {
                returnToFront.add(delegation);
            }
        }
        return ResponseEntity.ok(returnToFront);
    }
    /**
     * this mapping is used to change the row boolean is_active to true
     * @param username in row to set to true
     * @return the is_active status of the row with specified username if succesful.
     */
    @PutMapping("/changeActiveTrue/{username}")
    public ResponseEntity<?> changeActive(@PathVariable("username") String username){
        Optional <User> user = userRepository.findByUsername(username);
        List<EvacActive> evacActiveList = evacActiveRepository.findAll();

        for(EvacActive evacActive: evacActiveList) {

            if(evacActive.getUsername().equals(username)){

                for (Role role : user.get().getRoles()) {

                    if (role.getName().equals(ERole.ROLE_EVACLEADER)) {
                        Optional <EvacActive> evacActive2 = evacActiveRepository.findByUsername(username);
                        EvacActive newEvacActive = evacActive2.get();
                        newEvacActive.setActiveTrue();
                        evacActiveRepository.findByUsername(username)
                                .map(updatedDeputy -> this.evacActiveRepository.save(newEvacActive));

                        return ResponseEntity.ok
                                ("deputyleader activity status changed: " + evacActive2.get().isActive());
                    }
                }

                return ResponseEntity
                        .badRequest()
                        .body("Invalid role");
            }
        }
        return ResponseEntity
                .badRequest()
                .body("no evacleader with this username");
    }
    /**
     * this mapping is used to change the row boolean is_active to false
     * @param username in row to set to false
     * @return the is_active status of the row with specified username if succesful.
     */

    @PutMapping("/changeActiveFalse/{username}")
    public ResponseEntity<?> changeActiveF(@PathVariable("username") String username){
        Optional <User> user = userRepository.findByUsername(username);
        List<EvacActive> evacActiveList = evacActiveRepository.findAll();

        for(EvacActive evacActive: evacActiveList) {

            if(evacActive.getUsername().equals(username)){

                for (Role role : user.get().getRoles()) {

                    if (role.getName().equals(ERole.ROLE_EVACLEADER)) {
                        Optional <EvacActive> evacActive2 = evacActiveRepository.findByUsername(username);
                        EvacActive newEvacActive = evacActive2.get();
                        newEvacActive.setActiveFalse();
                        evacActiveRepository.findByUsername(username)
                                .map(updatedDeputy -> this.evacActiveRepository.save(newEvacActive));

                        return ResponseEntity.ok
                                ("deputyleader activity status changed: " + evacActive2.get().isActive());
                    }
                }

                return ResponseEntity
                        .badRequest()
                        .body("Invalid role");
            }
        }
        return ResponseEntity
                .badRequest()
                .body("no evacleader with this username");
    }

    /**
     * this mapping is used to set all columns is_active of table
     * evac_active to false
     * @return Responseentity.ok
     */

    @PutMapping("/turnOffAllActive")
    public ResponseEntity<?> turnOffAllActive() {
        List<EvacActive> evacActiveList = evacActiveRepository.findAll();

        for (EvacActive evacActive : evacActiveList) {
            String username = evacActive.getUsername();
            EvacActive newEvacActive = evacActive;
            newEvacActive.setActiveFalse();
            evacActiveRepository.findByUsername(username)
                    .map(updatedDeputy -> this.evacActiveRepository.save(newEvacActive));
        }
        return ResponseEntity.ok("all active: true evacuation leaders set to false");
    }

    /**
     * This method gets all the active status available
     * @return all the active status in the database
     */
    @GetMapping("/getAllActiveStatus")
    public List <EvacActive> getAllActiveStatus() {
        return evacActiveRepository.findAll();
    }


}

