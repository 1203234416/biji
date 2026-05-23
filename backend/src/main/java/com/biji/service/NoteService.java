package com.biji.service;

import com.biji.model.Note;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoteService {

    @Value("${biji.notes-dir:notes}")
    private String notesDirPath;

    private Path notesDir;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostConstruct
    public void init() throws IOException {
        notesDir = Paths.get(notesDirPath);
        if (!Files.exists(notesDir)) {
            Files.createDirectories(notesDir);
        }
    }

    public List<Note> listNotes() {
        try {
            return Files.list(notesDir)
                    .filter(f -> f.toString().endsWith(".txt"))
                    .map(this::readNoteFromFile)
                    .filter(Objects::nonNull)
                    .sorted((a, b) -> b.getUpdateTime().compareTo(a.getUpdateTime()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public Note getNote(String id) {
        Path file = notesDir.resolve(id + ".txt");
        if (!Files.exists(file)) return null;
        return readNoteFromFile(file);
    }

    public Note createNote(String title, String content) {
        if (title == null || title.isBlank()) {
            title = "未命名笔记";
        }
        String id = UUID.randomUUID().toString().substring(0, 8);
        Note note = new Note(id, title, content != null ? content : "", LocalDateTime.now(), LocalDateTime.now());
        saveNoteToFile(note);
        return note;
    }

    public Note updateNote(String id, String title, String content) {
        Note note = getNote(id);
        if (note == null) return null;
        if (title != null && !title.isBlank()) {
            note.setTitle(title);
        }
        if (content != null) {
            note.setContent(content);
        }
        note.setUpdateTime(LocalDateTime.now());
        saveNoteToFile(note);
        return note;
    }

    public boolean deleteNote(String id) {
        try {
            return Files.deleteIfExists(notesDir.resolve(id + ".txt"));
        } catch (IOException e) {
            return false;
        }
    }

    private Note readNoteFromFile(Path file) {
        try {
            List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
            if (lines.isEmpty()) return null;

            String title = lines.get(0);
            String content = lines.size() > 1
                    ? String.join("\n", lines.subList(1, lines.size()))
                    : "";

            BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
            LocalDateTime createTime = LocalDateTime.ofInstant(
                    attrs.creationTime().toInstant(), ZoneId.systemDefault());
            LocalDateTime updateTime = LocalDateTime.ofInstant(
                    attrs.lastModifiedTime().toInstant(), ZoneId.systemDefault());

            String filename = file.getFileName().toString();
            String id = filename.substring(0, filename.lastIndexOf('.'));

            return new Note(id, title, content, createTime, updateTime);
        } catch (IOException e) {
            return null;
        }
    }

    private void saveNoteToFile(Note note) {
        Path file = notesDir.resolve(note.getId() + ".txt");
        try {
            List<String> lines = new ArrayList<>();
            lines.add(note.getTitle());
            if (note.getContent() != null && !note.getContent().isEmpty()) {
                lines.addAll(Arrays.asList(note.getContent().split("\n", -1)));
            }
            Files.write(file, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save note", e);
        }
    }
}
