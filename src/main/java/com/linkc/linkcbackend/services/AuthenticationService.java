package com.linkc.linkcbackend.services;

import com.linkc.linkcbackend.domain.*;
import com.linkc.linkcbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User.UserBuilder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .number(request.getNumber())
                .role(Role.USER)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse.AuthenticationResponseBuilder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception{
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isPresent()) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            String jwtToken = jwtService.generateToken(user.get());

            return new AuthenticationResponse.AuthenticationResponseBuilder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new Exception("User not found");
        }
    }

    public void changePassword(User user, String oldPassword, String newPassword) throws Exception {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new Exception("Old password does not match existing password.");
        }
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }
}
