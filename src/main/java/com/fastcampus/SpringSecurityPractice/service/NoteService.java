package com.fastcampus.SpringSecurityPractice.service;

import com.fastcampus.SpringSecurityPractice.constant.Role;
import com.fastcampus.SpringSecurityPractice.domain.admin.Admin;
import com.fastcampus.SpringSecurityPractice.domain.note.Note;
import com.fastcampus.SpringSecurityPractice.domain.note.NoteRegisterDto;
import com.fastcampus.SpringSecurityPractice.domain.user.User;
import com.fastcampus.SpringSecurityPractice.exception.ObjectNotFoundException;
import com.fastcampus.SpringSecurityPractice.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    /**
     * 유저가 본인이 작성한 Note 들을 조회
     * @param user
     * @return
     */
    public List<Note> findByUser(User user) {
        if (user == null) {
            throw new ObjectNotFoundException();
        }

        return noteRepository.findByUserOrderByIdDesc(user);
    }

    /**
     * Admin 이 모든 유저들이 작성한 모든 Note 들을 조회
     * @param admin
     * @return
     */
    public List<Note> findAllWhenAdmin(Admin admin) {
        if (admin == null) {
            throw new ObjectNotFoundException();
        }

        if (!admin.getAuthority().equals(Role.ADMIN)) {
            throw new IllegalStateException();
        }

        return noteRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    /**
     * Save Note
     * @param noteRegisterDto
     * @return
     */
    @Transactional
    public Note saveNote(@RequestBody NoteRegisterDto noteRegisterDto) {
        User user = noteRegisterDto.getUser();
        String title = noteRegisterDto.getTitle();
        String content = noteRegisterDto.getContent();

        Note note = Note.of(user, title, content);
        return noteRepository.save(note);
    }

    @Transactional
    public void deleteNote(Long noteId) {
        Optional<Note> note = noteRepository.findById(noteId);
        if (note == null) {
            throw new ObjectNotFoundException();
        }

        noteRepository.delete(note.get());
    }

}
