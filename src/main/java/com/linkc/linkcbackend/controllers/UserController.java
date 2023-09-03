package com.linkc.linkcbackend.controllers;

import com.linkc.linkcbackend.domain.User;
import com.linkc.linkcbackend.repository.UserRepository;
import com.linkc.linkcbackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        userService.saveUser(user);

        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<?> getUser(Authentication authentication) {
        User authenticated_user = (User)authentication.getPrincipal();
        System.out.println(authenticated_user.getFirstName());

        Optional<User> user = userRepository.findById(authenticated_user.getId());

        if (user.isEmpty()) {
            return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
    }

    @GetMapping("/email/{id}")
    public ResponseEntity<?> getEmail(@PathVariable String id) {
        Optional<User> user = userRepository.findByEmail(id);

        if(user.isEmpty()) {
            return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
    }
}
