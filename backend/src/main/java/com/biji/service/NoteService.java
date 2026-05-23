package com.biji.service;

import com.biji.model.Note;
import com.biji.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class NoteService {

    private final NoteRepository repo;

    public NoteService(NoteRepository repo) {
        this.repo = repo;
    }

    public List<Note> listNotes() {
        return repo.findAllByOrderByUpdateTimeDesc();
    }

    public Note getNote(String id) {
        return repo.findById(id).orElse(null);
    }

    public Note createNote(String title, String content) {
        if (title == null || title.isBlank()) {
            title = "未命名笔记";
        }
        Note note = new Note();
        note.setId(UUID.randomUUID().toString().substring(0, 8));
        note.setTitle(title);
        note.setContent(content != null ? content : "");
        note.setCreateTime(LocalDateTime.now());
        note.setUpdateTime(LocalDateTime.now());
        return repo.save(note);
    }

    public Note updateNote(String id, String title, String content) {
        Note note = repo.findById(id).orElse(null);
        if (note == null) return null;
        if (title != null && !title.isBlank()) {
            note.setTitle(title);
        }
        if (content != null) {
            note.setContent(content);
        }
        note.setUpdateTime(LocalDateTime.now());
        return repo.save(note);
    }

    public boolean deleteNote(String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
