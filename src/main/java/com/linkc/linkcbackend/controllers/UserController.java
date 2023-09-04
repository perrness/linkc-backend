package com.linkc.linkcbackend.controllers;

import com.linkc.linkcbackend.domain.Response;
import com.linkc.linkcbackend.domain.User;
import com.linkc.linkcbackend.domain.UserData;
import com.linkc.linkcbackend.repository.UserRepository;
import com.linkc.linkcbackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<?> getUser(Authentication authentication) {
        User authenticated_user = (User)authentication.getPrincipal();

        Optional<User> user = userRepository.findById(authenticated_user.getId());

        if (user.isEmpty()) {
            return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
        } else {
            UserData userData = new UserData.builder()
                    .firstname(user.get().getFirstname())
                    .lastname(user.get().getLastname())
                    .email(user.get().getEmail())
                    .number(user.get().getNumber())
                    .build();
            return new ResponseEntity<>(userData, HttpStatus.OK);
        }
    }

    @PatchMapping("")
    public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody Map<String, Object> updates) {
        User authenticated_user = (User)authentication.getPrincipal();

        Optional<User> user = userRepository.findById(authenticated_user.getId());

        if (user.isEmpty()) {
            return new ResponseEntity<>(new Response("User not found"), HttpStatus.NOT_FOUND);
        } else {
            try {
                userService.updateUser(user.get(), updates);
            } catch (Exception exception) {
                return new ResponseEntity<>(new Response(exception.getMessage()), HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(new Response("User updated."), HttpStatus.OK);
        }
    }
}
