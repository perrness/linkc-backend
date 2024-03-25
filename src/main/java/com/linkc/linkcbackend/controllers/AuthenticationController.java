package com.linkc.linkcbackend.controllers;

import com.linkc.linkcbackend.domain.AuthenticationRequest;
import com.linkc.linkcbackend.domain.AuthenticationResponse;
import com.linkc.linkcbackend.domain.RegisterRequest;
import com.linkc.linkcbackend.domain.Response;
import com.linkc.linkcbackend.services.AuthenticationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        logger.info("Register new account");

        try {
            AuthenticationResponse authenticationResponse = authenticationService.register(request);
            return ResponseEntity.ok(authenticationResponse);
        } catch (Exception exception) {
            logger.error("Register failed with: {}", exception.getMessage());
            if (exception.getMessage().contains("Duplicate key violation on the requested collection")) {
                return ResponseEntity.badRequest().body(new Response("Email or number already in use."));
            }
            return ResponseEntity.internalServerError().body("Something failed during registration");
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(request));
        } catch (Exception exception) {
            if (exception.getMessage().contains("User not found")) {
                return new ResponseEntity<>(new Response(exception.getMessage()), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(new Response(exception.getMessage()), HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
