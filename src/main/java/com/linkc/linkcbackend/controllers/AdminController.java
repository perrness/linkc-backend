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
        List<UserData> userData = new ArrayList<>();

        users.forEach(user -> {
            userData.add(new UserData.builder()
                    .email(user.getEmail())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .number(user.getNumber())
                    .profilePictureUri(user.getProfilePictureUri())
                    .build());
        });

        if (users.isEmpty()) {
            return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userData, HttpStatus.OK);
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
}
