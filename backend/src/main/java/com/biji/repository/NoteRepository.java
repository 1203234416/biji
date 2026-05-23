package com.biji.repository;

import com.biji.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, String> {
    List<Note> findAllByOrderByUpdateTimeDesc();
}
