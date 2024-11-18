package com.springboot.backend.angel.usersapp.respositories;

import com.springboot.backend.angel.usersapp.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
