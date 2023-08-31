package com.linkc.linkcbackend.services;

import com.linkc.linkcbackend.models.Box;
import com.linkc.linkcbackend.models.User;
import com.linkc.linkcbackend.repository.BoxRepository;
import com.linkc.linkcbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        userRepository.insert(user);
    }
}
