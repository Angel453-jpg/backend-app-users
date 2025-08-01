package com.springboot.backend.angel.usersapp.services;

import com.springboot.backend.angel.usersapp.entities.Role;
import com.springboot.backend.angel.usersapp.entities.User;
import com.springboot.backend.angel.usersapp.models.IUser;
import com.springboot.backend.angel.usersapp.models.UserRequest;
import com.springboot.backend.angel.usersapp.respositories.RoleRepository;
import com.springboot.backend.angel.usersapp.respositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UseServiceImpl implements UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UseServiceImpl(UserRepository repository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return ((List<User>) repository.findAll()).stream().map(user -> {
            boolean admin = user.getRoles().stream().anyMatch(role -> "ROLE_ADMIN".equals(role.getName()));
            user.setAdmin(admin);
            return user;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(user -> {
            boolean admin = user.getRoles().stream().anyMatch(role -> "ROLE_ADMIN".equals(role.getName()));
            user.setAdmin(admin);
            return user;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {

        user.setRoles(getRoles(user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);

    }

    @Override
    @Transactional
    public Optional<User> update(UserRequest user, Long id) {

        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {

            User userDb = userOptional.orElseThrow();
            userDb.setName(user.getName());
            userDb.setLastName(user.getLastName());
            userDb.setEmail(user.getEmail());
            userDb.setUsername(user.getUsername());

            List<Role> roles = getRoles(user);

            userDb.setRoles(roles);
            return Optional.of(repository.save(userDb));
        }

        return Optional.empty();

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private List<Role> getRoles(IUser user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        optionalRoleUser.ifPresent(roles::add);

        if (user.isAdmin()) {
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }
        return roles;
    }

}
