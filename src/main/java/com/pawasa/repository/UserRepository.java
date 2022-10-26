package com.pawasa.repository;

import com.pawasa.model.Role;
import com.pawasa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
    User findByUsernameOrEmail(String username, String email);

    List<User> findAllByRole(Role role);
}
