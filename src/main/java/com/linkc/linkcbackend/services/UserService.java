package com.linkc.linkcbackend.services;

import com.linkc.linkcbackend.domain.User;
import com.linkc.linkcbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        if (updates.containsKey("email")) {
            user.setEmail((String) updates.get("email"));
        }

        if (updates.containsKey("number")) {
            user.setNumber((String) updates.get("number"));
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
