package com.example.exemplo_web.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class User {

    private Long id;

    @NotNull(message = "Name should be informed.")
    @Size(min = 5, max = 50, message = "Name size should be between {min} and {max}.")
    private String name;

    @NotNull(message = "E-mail should be informed.")
    @Email(message = "Invalid e-mail.")
    private String email;

    public User() {}

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
