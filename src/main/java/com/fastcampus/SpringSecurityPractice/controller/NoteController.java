package com.fastcampus.SpringSecurityPractice.controller;

import com.fastcampus.SpringSecurityPractice.domain.note.Note;
import com.fastcampus.SpringSecurityPractice.domain.note.NoteRegisterDto;
import com.fastcampus.SpringSecurityPractice.domain.user.User;
import com.fastcampus.SpringSecurityPractice.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    /**
     * 유저가 작성한 Note 들을 조회해서 랜더링 하기
     * @param model
     * @param authentication
     * @return
     */
    @GetMapping("/")
    public String getNotes(Model model, Authentication authentication) {
        List<Note> notes = noteService.findByUser(new User());
        model.addAttribute("notes", notes);

        return "note/index";
    }

    @GetMapping("/admin")
    public String getNotesAdmin(Model model, Authentication authentication) {
        List<Note> notes = noteService.findByUser(new User());
        model.addAttribute("notes", notes);

        return "admin/index";
    }

    /**
     * 노트 저장
     */
    @PostMapping("/")
    public String saveNote(@RequestBody NoteRegisterDto noteRegisterDto) {
        noteService.saveNote(noteRegisterDto);

        return "redirect:note";
    }

    /**
     * 노트 삭제
     */
    @DeleteMapping
    public String deleteNote(@RequestParam Long id) {
        noteService.deleteNote(id);
        return "redirect:note";
    }
}
