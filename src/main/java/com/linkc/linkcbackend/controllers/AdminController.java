package com.linkc.linkcbackend.controllers;

import com.linkc.linkcbackend.domain.*;
import com.linkc.linkcbackend.repository.UserRepository;
import com.linkc.linkcbackend.services.AuthenticationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public AdminController(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUser(Authentication authentication) {
        List<User> users = userRepository.findAll();
        List<UserData> userDataList = new ArrayList<>();

        users.forEach(user -> {
            userDataList.add(new UserData.Builder()
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .number(user.getNumber())
                    .email(user.getEmail())
                    .profilePictureUri(user.getProfilePictureUri())
                    .id(user.getId())
                    .role(user.getRole())
                    .build());
        });

        if (users.isEmpty()) {
            return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userDataList, HttpStatus.OK);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> register(@Valid @RequestBody AdminRegisterRequest request) {
        logger.info("Admin register new account");

        try {
            UserData user = authenticationService.adminRegister(request);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception exception) {
            logger.error("Register failed with: {}", exception.getMessage());
            if (exception.getMessage().contains("E11000 duplicate key error collection: linkc.users. Failed _id or unique index constraint.")) {
                return ResponseEntity.badRequest().body(new Response("Email or number already in use."));
            }
            return ResponseEntity.internalServerError().body("Something failed during registration");
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        logger.info("Admin getting user " + id);

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
        } else {
            UserData userData = new UserData.Builder()
                    .firstname(user.get().getFirstname())
                    .lastname(user.get().getLastname())
                    .number(user.get().getNumber())
                    .email(user.get().getEmail())
                    .profilePictureUri(user.get().getProfilePictureUri())
                    .id(user.get().getId())
                    .role(user.get().getRole())
                    .build();
            return new ResponseEntity<>(userData, HttpStatus.OK);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable String id) {
        logger.info("Admin deleting user");

        try {
            userRepository.deleteById(id);
            return ResponseEntity.ok("Deleted " + id);
        } catch (Exception exception) {
            logger.error("Admin deleting user failed with: {}", exception.getMessage());
            return ResponseEntity.internalServerError().body("Something failed during deletion");
        }
    }
}
