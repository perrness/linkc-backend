package com.linkc.linkcbackend.controllers;

import com.linkc.linkcbackend.domain.Response;
import com.linkc.linkcbackend.domain.User;
import com.linkc.linkcbackend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/god")
public class GodController {
    private final UserRepository userRepository;

    public GodController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @DeleteMapping("/delete/{number}")
    public ResponseEntity<?> deleteUser(@PathVariable String number) {
        Optional<User> user = userRepository.deleteByNumber(number);

        return new ResponseEntity<>(new Response("User deleted"), HttpStatus.OK);
    }
}
