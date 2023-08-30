package com.linkc.linkcbackend.controllers;

import com.linkc.linkcbackend.models.User;
import com.linkc.linkcbackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> add(@RequestBody User user) {
        userService.saveUser(user);

        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }
}
