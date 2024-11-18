package com.springboot.backend.angel.usersapp.models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest implements IUser {

    @NotBlank(message = "No puede estar vació")
    private String name;

    @Column(name = "last_name")
    @NotBlank(message = "no puede estar vació")
    private String lastName;

    @NotBlank(message = "no puede estar vació")
    @Email(message = "no es una dirección de correo valida")
    private String email;

    @NotBlank(message = "no puede estar vació")
    @Size(min = 4, max = 12, message = "el tamaño tiene que estar entre 4 y 12 caracteres")
    private String username;

    private boolean admin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
