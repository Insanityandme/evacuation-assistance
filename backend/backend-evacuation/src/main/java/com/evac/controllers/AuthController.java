package com.evac.controllers;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import com.evac.models.*;
import com.evac.repository.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.evac.payload.request.LoginRequest;
import com.evac.payload.request.SignupRequest;
import com.evac.payload.response.JwtResponse;
import com.evac.security.jwt.JwtUtils;
import com.evac.security.services.UserDetailsImpl;
import javax.validation.Valid;

/**
 * This class is a RestController that will manage HTTP requests from the user to the server
 * AuthController is used for user and role registration and authentication.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    DeputyRepository deputyRepository;
    @Autowired
    private EvacActiveRepository evacActiveRepository;
    @Autowired
    private UserSensorPosRepository userSensorPosRepository;


    /**
     * This method authenticates the user information provided by the user by using a LoginRequest object. It will check if the user is valid or not.
     *
     * @param loginRequest --> object that contains the user credentials (username and password).
     * @return a response in case the authentication is valid.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        return ResponseEntity.ok( new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    /**
     * This method sends a request to add a role to the roles table in the database.
     *
     * @param role --> The role to be added
     * @return an ok-response in case the role has been added or a bad request in case not.
     */
    @PostMapping("/addRole")
    public ResponseEntity<?> addRole(@RequestBody Role role) {
        try {
            Role newRole = roleRepository.save(role);
            return ResponseEntity.ok(newRole);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not add role: " + e.getMessage());
        }
    }

    /**
     * This method sends a request to get all the roles available in the database
     *
     * @return all the roles in the database
     */
    @GetMapping("/getAllRoles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    /**
     * This method is a request to sign up as a new user by using a SignupRequest object (json body)
     *
     * @param signUpRequest --> Object that will contain the new user's credentials
     * @return an ok-response in case the user is created or a bad request in case there is a problem.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {

                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;

                    case "deputy":
                        Role modRole = roleRepository.findByName(ERole.ROLE_DEPUTYLEADER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        /*
                         the two lines below adds a row to the table deputies with
                         a username from the user registered, and a boolean isActive
                         It is added here because only deputy leaders should be in this table.
                         */

                        //User with deputy-role will be added to the deputy table in the database
                        Deputy deputy = new Deputy(user.getUsername());
                        deputyRepository.save(deputy);
                        break;

                    case "evac":
                        Role evacRole = roleRepository.findByName(ERole.ROLE_EVACLEADER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(evacRole);

                        EvacActive evacActive = new EvacActive(user.getUsername());
                        evacActiveRepository.save(evacActive);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                        String username = signUpRequest.getUsername();
                        if(userSensorPosRepository.existsByUsername(username)) {
                            userSensorPosRepository.deleteByUsername(username);
                        }
                        LocalDateTime localDateTime = LocalDateTime.now();
                        UserSensorPos userSensorPos = new UserSensorPos(localDateTime, username);
                        userSensorPosRepository.save(userSensorPos);

                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }


    /**
     * This method is a request to get a user using his/her id
     *
     * @param userId --> id of the user
     * @return the user with given id, or null if there isn't one
     */
    @GetMapping("/getUserById/{userId}")
    public Optional<User> getUserById(@PathVariable("userId") Long userId) {
        Optional<User> user = userRepository.findById(userId);

        return user;
    }


    /**
     * This method is a request to delete a user by id
     *
     * @param userId --> the id of the user to be deleted
     * @return an ok-response in case the user has been deleted or a bad request in case there is no user with the given id
     */
    @DeleteMapping("/deleteById/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long userId) {
        if (userRepository.existsById(userId)) {
            this.userRepository.deleteById(userId);

            return ResponseEntity.ok("User with id " + userId + " deleted successfully!");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("No user with the id provided!");
        }
    }

    /**
     * This method is a request to get all the users
     *
     * @return all the users in the database
     */
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * This method is a request to get a user by his/her username
     *
     * @param userName --> the username of the user to get
     * @return an ok-response with information of the user
     */
    @GetMapping("/getUserByUserName/{UserName}")
    //Check if it should give a bad request in case there is no user with given username
    public ResponseEntity<?> getUserByUserName(@PathVariable("UserName") String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        return ResponseEntity.ok("The User was found; userName: " + user.getUsername() + ", email: " + user.getEmail());
    }

    /**
     * This method is a request to modify a user's username using his/her id
     *
     * @param userId      --> the id of the user to be modified
     * @param updatedUser --> json body that will contain the new username
     * @return the user with updated username
     */
    @PutMapping("/changeUserNameById/{UserId}")
    public ResponseEntity<?> changeUserNameById(@PathVariable("UserId") Long userId, @RequestBody User updatedUser) {
        User oldUserWithNewUserName;
        if ((this.userRepository.findById(userId).isPresent())
                && !(this.userRepository.existsByUsername(updatedUser.getUsername()))) {
            oldUserWithNewUserName = this.userRepository.findById(userId).get();
            String oldUsername = oldUserWithNewUserName.getUsername();
            oldUserWithNewUserName.setUsername(updatedUser.getUsername());
            userRepository.findById(userId)
                    .map(newUser -> this.userRepository.save(oldUserWithNewUserName));
            return ResponseEntity.ok("user with id: " + userId + " username changed from: " +
                    oldUsername + ", changed to: " +
                    updatedUser.getUsername());
        } else {
            return ResponseEntity.badRequest().body("No user with this id, or user with new username alrdy in db ");
        }

    }

    /**
     * This method change the email of a user by taking his/her id.
     * @param userId -> User-id to identify the user.
     * @param updatedUser -> a new user object that will contain the new email.
     * @return -> ResponseEntity with a body-message in case the request went through or not.
     */
    @PutMapping("/changeEmailById/{UserId}")
    public ResponseEntity<?> changeEmailById(@PathVariable("UserId") Long userId, @RequestBody User updatedUser) {
        User oldUserWithNewEmail;
        if (userRepository.findById(userId).isPresent()) {
            oldUserWithNewEmail = this.userRepository.findById(userId).get();
            String oldEmail = oldUserWithNewEmail.getEmail();
            oldUserWithNewEmail.setEmail(updatedUser.getEmail());
            userRepository.findById(userId)
                    .map(newUser -> this.userRepository.save(oldUserWithNewEmail));
            return ResponseEntity.ok("user with id: " + userId + " email changed from: " +
                    oldEmail + ", changed to: " +
                    updatedUser.getEmail());
        } else {
            return ResponseEntity.badRequest().body("No user with this id ");
        }
    }

    /**
     * This method change the password of a user by using his/her id
     * @param userId -> the user-id to identify the user
     * @param updatedUser -> User object that will store the updated data of the user
     * @return -> A responseEntity with a body-message to check is the request went through or not
     */
    
    @PutMapping("/changePasswordById/{userId}")
    public ResponseEntity<?> changePasswordById(@PathVariable("userId") Long userId, @RequestBody User updatedUser) {
        User oldUserWithNewPassword;
        if(userRepository.findById(userId).isPresent()) {
            oldUserWithNewPassword = this.userRepository.findById(userId).get();
            String oldPassword = oldUserWithNewPassword.getPassword();
            oldUserWithNewPassword.setPassword(encoder.encode(updatedUser.getPassword()));
            userRepository.findById(userId)
                    .map(newUser -> this.userRepository.save(oldUserWithNewPassword));
            return ResponseEntity.ok("user with id: " + userId + " password change from: " +
                    oldPassword + ", changed to: " + updatedUser.getPassword());
        } else {
            return ResponseEntity.badRequest().body("No user with this id");
        }

    }

    /**
     * This method change the role of a user by using his/her id
     * @param userId -> User-id that will identify the user
     * @param signupRequest -> SignRequest object that will have the updated data.
     * @return ->  ResponseEntity with body-message to check if the request went through or not
     */
    @PutMapping("/changeRoleById/{userId}")
    public ResponseEntity<?> changeRoleById(@PathVariable("userId") Long userId, @RequestBody SignupRequest signupRequest) {
        User oldUserWithNewRole;
        if(userRepository.findById(userId).isPresent()) {
            oldUserWithNewRole = this.userRepository.findById(userId).get();
            Set<Role> oldRole = oldUserWithNewRole.getRoles();
            Set<String> strRoles = signupRequest.getRole();
            System.out.println(strRoles);
            Set<Role> roles = new HashSet<>();
            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {

                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);

                            break;

                        case "deputy":
                            Role modRole = roleRepository.findByName(ERole.ROLE_DEPUTYLEADER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(modRole);
                        /*
                         the two lines below adds a row to the table deputies with
                         a username from the user registered, and a boolean isActive
                         It is added here because only deputy leaders should be in this table.
                         */

                            //User with deputy-role will be added to the deputy table in the database
                            Deputy deputy = new Deputy(oldUserWithNewRole.getUsername());
                            deputyRepository.save(deputy);
                            break;

                        case "evac":
                            Role evacRole = roleRepository.findByName(ERole.ROLE_EVACLEADER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(evacRole);

                            EvacActive evacActive = new EvacActive(oldUserWithNewRole.getUsername());
                            evacActiveRepository.save(evacActive);
                            break;

                        default:
                            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                            String username = oldUserWithNewRole.getUsername();
                            if(userSensorPosRepository.existsByUsername(username)) {
                                userSensorPosRepository.deleteByUsername(username);
                            }
                            LocalDateTime localDateTime = LocalDateTime.now();
                            UserSensorPos userSensorPos = new UserSensorPos(localDateTime, username);
                            userSensorPosRepository.save(userSensorPos);

                    }
                });
            }

            oldUserWithNewRole.setRoles(roles);

            userRepository.findById(userId)
                    .map(newUser -> this.userRepository.save(oldUserWithNewRole));
            return ResponseEntity.ok(oldUserWithNewRole.getRoles());

        }
        return ResponseEntity.badRequest().body("bad!");

    }

}
