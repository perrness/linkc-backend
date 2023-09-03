package com.linkc.linkcbackend.controllers;

import com.linkc.linkcbackend.domain.Box;
import com.linkc.linkcbackend.domain.User;
import com.linkc.linkcbackend.repository.BoxRepository;
import com.linkc.linkcbackend.services.BoxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/box")
public class BoxController {
    private final BoxService boxService;

    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping("")
    public ResponseEntity<?> getBoxesReservedByUser(Authentication authentication) {
        User user = (User)authentication.getPrincipal();

        return new ResponseEntity<>(boxService.getBoxes(user.getId()), HttpStatus.OK);
    }
}
