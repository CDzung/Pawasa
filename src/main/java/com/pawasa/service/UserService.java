package com.pawasa.service;

import com.pawasa.exception.UserAlreadyExistsException;
import com.pawasa.model.User;

public interface UserService {
    public abstract void addUser(User user) throws UserAlreadyExistsException;
}
