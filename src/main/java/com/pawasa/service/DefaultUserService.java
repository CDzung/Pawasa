package com.pawasa.service;

import com.pawasa.exception.UserAlreadyExistsException;
import com.pawasa.model.User;
import com.pawasa.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository userRepository;


    private PasswordEncoder passwordEncoder = new PasswordEncoder() {
        @Override
        public String encode(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            return charSequence.toString().equals(s);
        }
    };

    @Override
    public void addUser(User user) throws UserAlreadyExistsException {
        User existingUser = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if(existingUser != null) {
            throw new UserAlreadyExistsException("User already exists with username: " + user.getUsername());
        }
        encodePassword(user);
        userRepository.save(user);
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
