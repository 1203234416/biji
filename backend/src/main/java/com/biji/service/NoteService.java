package com.biji.service;

import com.biji.model.Note;
import com.biji.repository.NoteRepository;
import com.biji.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class NoteService {

    private final NoteRepository noteRepo;
    private final UserRepository userRepo;

    public NoteService(NoteRepository noteRepo, UserRepository userRepo) {
        this.noteRepo = noteRepo;
        this.userRepo = userRepo;
    }

    public List<Note> listNotes(String userId) {
        return noteRepo.findByUserIdOrderByUpdateTimeDesc(userId);
    }

    public Note getNote(String id, String userId) {
        Note note = noteRepo.findById(id).orElse(null);
        if (note == null || !note.getUserId().equals(userId)) return null;
        return note;
    }

    public Note createNote(String userId, String title, String content) {
        if (title == null || title.isBlank()) {
            title = "未命名笔记";
        }
        Note note = new Note();
        note.setId(UUID.randomUUID().toString().substring(0, 8));
        note.setUserId(userId);
        note.setTitle(title);
        note.setContent(content != null ? content : "");
        note.setCreateTime(LocalDateTime.now());
        note.setUpdateTime(LocalDateTime.now());
        return noteRepo.save(note);
    }

    public Note updateNote(String id, String userId, String title, String content) {
        Note note = noteRepo.findById(id).orElse(null);
        if (note == null || !note.getUserId().equals(userId)) return null;
        if (title != null && !title.isBlank()) {
            note.setTitle(title);
        }
        if (content != null) {
            note.setContent(content);
        }
        note.setUpdateTime(LocalDateTime.now());
        return noteRepo.save(note);
    }

    public boolean deleteNote(String id, String userId) {
        Note note = noteRepo.findById(id).orElse(null);
        if (note == null || !note.getUserId().equals(userId)) return false;
        noteRepo.deleteById(id);
        cleanupUser(userId);
        return true;
    }

    public int batchDelete(List<String> ids, String userId) {
        int count = 0;
        for (String id : ids) {
            if (deleteNote(id, userId)) count++;
        }
        return count;
    }

    private void cleanupUser(String userId) {
        if (noteRepo.findByUserIdOrderByUpdateTimeDesc(userId).isEmpty()) {
            userRepo.deleteById(userId);
        }
    }
}
