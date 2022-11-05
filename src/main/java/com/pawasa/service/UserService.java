package com.pawasa.service;

import com.pawasa.exception.UserAlreadyExistsException;
import com.pawasa.model.User;

public interface UserService {
    void addUser(User user) throws UserAlreadyExistsException;
    void changePassword(User user, String newPassword);
    String generatePassword();
}
