package com.beat.sbtutorial.service;

import com.beat.sbtutorial.repository.UserRepository;
import com.beat.sbtutorial.entity.User;
import com.beat.sbtutorial.exception.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        String username = user.getUsername().replace(" ", "");
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            throw new UserAlreadyExistsException("Username " + username + " already exists.");
        }
        user.setUsername(username);
        return userRepository.save(user);
    }


}
