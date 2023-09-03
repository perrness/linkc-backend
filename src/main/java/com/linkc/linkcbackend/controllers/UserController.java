package com.linkc.linkcbackend.controllers;

import com.linkc.linkcbackend.domain.ChangePasswordRequest;
import com.linkc.linkcbackend.domain.Response;
import com.linkc.linkcbackend.domain.User;
import com.linkc.linkcbackend.domain.UserData;
import com.linkc.linkcbackend.repository.UserRepository;
import com.linkc.linkcbackend.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public UserController(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
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

    @PutMapping("/password")
    public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody ChangePasswordRequest changePasswordRequest) {
        User authenticated_user = (User)authentication.getPrincipal();

        Optional<User> user = userRepository.findById(authenticated_user.getId());

        if (user.isEmpty()) {
            return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
        } else {
            try {
                authenticationService.changePassword(user.get(), changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
            } catch (Exception exception) {
                return new ResponseEntity<>(new Response(exception.getMessage()), HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(new Response("Password changed"), HttpStatus.OK);
        }
    }
}
