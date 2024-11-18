package com.springboot.backend.angel.usersapp.services;

import com.springboot.backend.angel.usersapp.entities.User;
import com.springboot.backend.angel.usersapp.models.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    Optional<User> findById(Long id);

    User save(User user);

    Optional<User> update(UserRequest user, Long id);

    void deleteById(Long id);

}
