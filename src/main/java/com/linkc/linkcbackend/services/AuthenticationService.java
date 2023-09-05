package com.linkc.linkcbackend.services;

import com.linkc.linkcbackend.domain.*;
import com.linkc.linkcbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final AzureBlobService azureBlobService;

    @Value("linkc.defaultprofilepicture")
    private String defaultProfilePictureUri;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            AzureBlobService azureBlobService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.azureBlobService = azureBlobService;
    }

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        User user = new User.UserBuilder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .number(request.getNumber())
                .role(Role.USER)
                .build();

        if(request.getProfilePictureEncodedBase64() == null) {
            user.setProfilePictureUri(defaultProfilePictureUri);
        } else {
            String profilePictureUrl = azureBlobService.uploadImageToBlob(request.getProfilePictureEncodedBase64());
            user.setProfilePictureUri(profilePictureUrl);
        }

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
}
