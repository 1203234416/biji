package com.biji.controller;

import com.biji.model.User;
import com.biji.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<User> list() {
        return userRepo.findAll();
    }

    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        if (username == null || username.isBlank()) return null;
        return userRepo.findByUsername(username).orElseGet(() -> {
            User user = new User(UUID.randomUUID().toString().substring(0, 8), username);
            return userRepo.save(user);
        });
    }
}
