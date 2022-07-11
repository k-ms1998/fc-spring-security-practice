package com.fastcampus.SpringSecurityPractice.repository;

import com.fastcampus.SpringSecurityPractice.domain.note.Note;
import com.fastcampus.SpringSecurityPractice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUserOrderByIdDesc(User user);
}
