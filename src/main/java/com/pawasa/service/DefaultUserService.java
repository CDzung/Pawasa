package com.pawasa.service;

import com.pawasa.exception.UserAlreadyExistsException;
import com.pawasa.model.User;
import com.pawasa.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository userRepository;


    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final static String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";


    @Override
    public void
    addUser(User user) throws UserAlreadyExistsException {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser != null) {
            throw new UserAlreadyExistsException("User already exists with email: " + user.getEmail());
        }
        if(!user.getPassword().matches(passwordRegex)) {
            throw new IllegalArgumentException("Password must contain at least one digit, one lowercase, one uppercase, one special character and must be at least 8 characters long.");
        }
        encodePassword(user);
        userRepository.save(user);
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public String encodePassword(String password) {

        return passwordEncoder.encode(password);
    }
}

