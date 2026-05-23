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
    public List<Note> list() {
        return noteService.listNotes();
    }

    @GetMapping("/{id}")
    public Note get(@PathVariable String id) {
        return noteService.getNote(id);
    }

    @PostMapping
    public Note create(@RequestBody Map<String, String> body) {
        return noteService.createNote(body.get("title"), body.get("content"));
    }

    @PutMapping("/{id}")
    public Note update(@PathVariable String id, @RequestBody Map<String, String> body) {
        return noteService.updateNote(id, body.get("title"), body.get("content"));
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable String id) {
        boolean ok = noteService.deleteNote(id);
        return Map.of("success", ok);
    }
}
