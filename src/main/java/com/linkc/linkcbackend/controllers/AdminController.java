package com.linkc.linkcbackend.controllers;

import com.linkc.linkcbackend.domain.User;
import com.linkc.linkcbackend.domain.UserData;
import com.linkc.linkcbackend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
