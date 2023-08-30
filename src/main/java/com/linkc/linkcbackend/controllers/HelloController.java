package com.linkc.linkcbackend.controllers;

import com.linkc.linkcbackend.models.User;
import com.linkc.linkcbackend.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private final UserService userService;

    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public void add(@RequestParam(value = "name", defaultValue = "World") String name) {
        userService.saveUser(new User("1", name, "lol"));
    }
}
