package com.example.exemplo_web.service;

import com.example.exemplo_web.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static List<User> userList;

    @PostConstruct
    public void setup() {
        userList = new ArrayList<>(List.of(
                new User(1L, "Cris", "cris@email.com"),
                new User(2L, "Ana", "ana@email.com"),
                new User(3L, "Benja", "benja@email.com"),
                new User(4L, "Fred", "fred@email.com")
        ));
    }

    public List<User> findAll() {
        return userList;
    }

    public User findById(Long id) {
        return userList.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
    }

    public void add(User user) {
        Long maxId = userList.stream()
                        .map(User::getId)
                        .max(Long::compareTo)
                        .orElse(0L);
        user.setId(maxId + 1);
        userList.add(user);
    }

    public void update(long id, User user) {
        User userToUpdate = findById(id);
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
    }

    public void delete(User user) {
        userList.removeIf(u -> u.getId().equals(user.getId()));
    }

}
