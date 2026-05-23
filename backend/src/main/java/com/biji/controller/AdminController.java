package com.biji.controller;

import com.biji.model.Note;
import com.biji.model.User;
import com.biji.repository.NoteRepository;
import com.biji.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static final String PASSWORD = "412411bjw";

    private final UserRepository userRepo;
    private final NoteRepository noteRepo;

    public AdminController(UserRepository userRepo, NoteRepository noteRepo) {
        this.userRepo = userRepo;
        this.noteRepo = noteRepo;
    }

    @PostMapping("/stats")
    public Object stats(@RequestBody Map<String, String> body) {
        if (!PASSWORD.equals(body.get("password"))) {
            return Map.of("error", "wrong password");
        }
        List<User> users = userRepo.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (User u : users) {
            List<Note> notes = noteRepo.findByUserIdOrderByUpdateTimeDesc(u.getId());
            Map<String, Object> info = new LinkedHashMap<>();
            info.put("username", u.getUsername());
            info.put("count", notes.size());
            result.add(info);
        }
        return Map.of("users", result, "totalUsers", users.size());
    }
}
