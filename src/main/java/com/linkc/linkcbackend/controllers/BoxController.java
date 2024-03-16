package com.linkc.linkcbackend.controllers;

import com.linkc.linkcbackend.domain.*;
import com.linkc.linkcbackend.repository.BoxRepository;
import com.linkc.linkcbackend.services.BoxService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/box")
public class BoxController {
    private final BoxService boxService;
    private final BoxRepository boxRepository;

    public BoxController(BoxService boxService, BoxRepository boxRepository) {
        this.boxService = boxService;
        this.boxRepository = boxRepository;
    }

    @GetMapping("")
    public ResponseEntity<?> getBoxesReservedByUser(Authentication authentication) {
        User user = (User)authentication.getPrincipal();

        return new ResponseEntity<>(boxService.getBoxes(user.getId()), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> reserveBox(Authentication authentication, @Valid @RequestBody BoxReserveRequest boxReserveRequest) {
        User user = (User) authentication.getPrincipal();

        Optional<Box> box = boxRepository.findById(boxReserveRequest.getId());

        if (box.isEmpty()) {
            return new ResponseEntity<>(new Response("Box not found"), HttpStatus.NOT_FOUND);
        } else {
            try {
                boxService.reserveBox(user, box.get(), boxReserveRequest);
            } catch (Exception exception) {
                return new ResponseEntity<>(new Response(exception.getMessage()), HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(new Response("Box updated."), HttpStatus.OK);
        }
    }

    @PostMapping("/open")
    public ResponseEntity<?> openBox(Authentication authentication, @Valid @RequestBody BoxOpenRequest boxOpenRequest) {
        User user = (User) authentication.getPrincipal();

        Optional<Box> box = boxRepository.findById(boxOpenRequest.getId());

        if (box.isEmpty()) {
            return new ResponseEntity<>(new Response("Box not found"), HttpStatus.NOT_FOUND);
        } else {
            try {
                boxService.openBox(user, box.get());
            } catch (Exception exception) {
                return new ResponseEntity<>(new Response(exception.getMessage()), HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(new Response("Box updated."), HttpStatus.OK);
        }
    }

    @PostMapping("/close")
    public ResponseEntity<?> closeReservation(Authentication authentication, @Valid @RequestBody BoxOpenRequest boxOpenRequest) {
        User user = (User) authentication.getPrincipal();

        Optional<Box> box = boxRepository.findById(boxOpenRequest.getId());

        if (box.isEmpty()) {
            return new ResponseEntity<>(new Response("Box not found"), HttpStatus.NOT_FOUND);
        } else {
            try {
                boxService.closeReservation(user, box.get());
            } catch (Exception exception) {
                return new ResponseEntity<>(new Response(exception.getMessage()), HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(new Response("Box updated."), HttpStatus.OK);
        }
    }
}
