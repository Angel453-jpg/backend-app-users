package com.springboot.backend.angel.usersapp.controllers;

import com.springboot.backend.angel.usersapp.entities.User;
import com.springboot.backend.angel.usersapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> list() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {

        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userOptional.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "El usuario no se encontró por el id: " + id));

    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {

        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {

            User userDb = userOptional.orElseThrow();
            userDb.setName(user.getName());
            userDb.setLastName(user.getLastName());
            userDb.setEmail(user.getEmail());
            userDb.setUsername(user.getUsername());
            userDb.setPassword(user.getPassword());

            return ResponseEntity.status(HttpStatus.OK).body(userService.save(userDb));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "El usuario no existe"));
    }

}
