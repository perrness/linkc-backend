package com.linkc.linkcbackend.services;

import com.linkc.linkcbackend.domain.*;
import com.linkc.linkcbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ParqioService parqioService;
    private final AuthenticationManager authenticationManager;
    private final AzureBlobService azureBlobService;

    @Value("${linkc.defaultprofilepicture}")
    private String defaultProfilePictureUri;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            ParqioService parqioService,
            AuthenticationManager authenticationManager,
            AzureBlobService azureBlobService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.parqioService = parqioService;
        this.authenticationManager = authenticationManager;
        this.azureBlobService = azureBlobService;
    }

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        User user = addUser(request, Role.ROLE_USER);

        var jwtToken = jwtService.generateToken(user);
        parqioService.sendSmsCode(user.getNumber());

        return new AuthenticationResponse.AuthenticationResponseBuilder()
                .token(jwtToken)
                .build();
    }

    public UserData adminRegister(AdminRegisterRequest request) throws Exception {
        User user = addUser(request, request.getRole());

        return new UserData.Builder()
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .profilePictureUri(user.getProfilePictureUri())
                .number(user.getNumber())
                .build();
    }

    private User addUser(RegisterRequest request, Role role) {
        User user = new User.UserBuilder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .number(request.getNumber())
                .role(role)
                .build();

        try {
            String profilePictureUrl = azureBlobService.uploadImageToBlob(request.getProfilePictureEncodedBase64());
            user.setProfilePictureUri(profilePictureUrl);
        } catch (Exception exception) {
            user.setProfilePictureUri(defaultProfilePictureUri);
        }

        userRepository.save(user);

        return user;
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
}
