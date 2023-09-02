package com.linkc.linkcbackend.controllers;

import com.linkc.linkcbackend.domain.Box;
import com.linkc.linkcbackend.repository.BoxRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoxController {
    private final BoxRepository boxRepository;

    public BoxController(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }


    @PostMapping("/box")
    public ResponseEntity<?> addBox(@RequestBody Box box) {
        boxRepository.save(box);

        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }

    @GetMapping("/box/{id}")
    public ResponseEntity<?> getBoxesReservedByUser(@PathVariable String id) {
        return new ResponseEntity<>(boxRepository.findBoxReservedByUserId(id), HttpStatus.OK);
    }
}
