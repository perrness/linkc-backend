package com.linkc.linkcbackend.services;

import com.linkc.linkcbackend.domain.User;
import com.linkc.linkcbackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AzureBlobService azureBlobService;
    private final ParqioService parqioService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AzureBlobService azureBlobService, ParqioService parqioService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.azureBlobService = azureBlobService;
        this.parqioService = parqioService;
    }

    public void saveUser(User user) {
        userRepository.insert(user);
    }

    public void updateUser(User user, Map<String, Object> updates) throws Exception {
        if (updates.containsKey("old_password") && updates.containsKey("new_password")) {
            user.setPassword(changePassword(user, (String) updates.get("old_password"), (String) updates.get("new_password")));
        }

        if (updates.containsKey("firstname")) {
            user.setFirstname((String) updates.get("firstname"));
        }

        if (updates.containsKey("lastname")) {
            user.setLastname((String) updates.get("lastname"));
        }

        if (updates.containsKey("old_email") && updates.containsKey("new_email")) {
            if (!Objects.equals(updates.get("old_email"), user.getEmail())) {
                throw new Exception("Old email does not match existing email");
            }
            user.setEmail((String) updates.get("new_email"));
        }

        if (updates.containsKey("old_number") && updates.containsKey("new_number")) {
            if (!Objects.equals(updates.get("old_number"), user.getNumber())) {
                throw new Exception("Old number does not match existing number");
            }
            user.setNumber((String) updates.get("new_number"));
        }

        if (updates.containsKey("profile_picture_encoded_base64")) {
            azureBlobService.deleteImageInContainer(user.getProfilePictureUri());
            String filename = azureBlobService.uploadImageToBlob((String) updates.get("profile_picture_encoded_base64"));
            user.setProfilePictureUri(filename);
        }

        if (updates.containsKey("sms_code")) {
            String apiKey = parqioService.getAPIKey(user.getNumber(), (String) updates.get("sms_code"));
            user.setParqioAPIKey(apiKey);
        }

        userRepository.save(user);
    }

    private String changePassword(User user, String oldPassword, String newPassword) throws Exception {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new Exception("Old password does not match existing password.");
        }

        return passwordEncoder.encode(newPassword);
    }
}
