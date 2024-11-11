package com.springboot.backend.angel.usersapp.services;

import com.springboot.backend.angel.usersapp.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    void deleteById(Long id);

}
