package com.biji.controller;

import com.biji.model.Note;
import com.biji.service.NoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> list(@RequestParam String userId) {
        return noteService.listNotes(userId);
    }

    @GetMapping("/{id}")
    public Note get(@PathVariable String id, @RequestParam String userId) {
        return noteService.getNote(id, userId);
    }

    @PostMapping
    public Note create(@RequestBody Map<String, String> body) {
        return noteService.createNote(body.get("userId"), body.get("title"), body.get("content"));
    }

    @PutMapping("/{id}")
    public Note update(@PathVariable String id, @RequestBody Map<String, String> body) {
        return noteService.updateNote(id, body.get("userId"), body.get("title"), body.get("content"));
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable String id, @RequestParam String userId) {
        boolean ok = noteService.deleteNote(id, userId);
        return Map.of("success", ok);
    }
}
