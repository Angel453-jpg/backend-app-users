package com.springboot.backend.angel.usersapp.respositories;


import com.springboot.backend.angel.usersapp.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    
}
