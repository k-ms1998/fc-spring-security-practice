package com.fastcampus.SpringSecurityPractice.config;

import com.fastcampus.SpringSecurityPractice.domain.admin.AdminRegisterDto;
import com.fastcampus.SpringSecurityPractice.domain.note.NoteRegisterDto;
import com.fastcampus.SpringSecurityPractice.domain.user.User;
import com.fastcampus.SpringSecurityPractice.domain.user.UserRegisterDto;
import com.fastcampus.SpringSecurityPractice.service.AdminService;
import com.fastcampus.SpringSecurityPractice.service.NoteService;
import com.fastcampus.SpringSecurityPractice.service.NoticeService;
import com.fastcampus.SpringSecurityPractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
@Profile(value = "!test")
public class InitializeDefaultDataConfig {

    private final UserService userService;
    private final AdminService adminService;
    private final NoteService noteService;
    private final NoticeService noticeService;

    /**
     * 유저 등록 & 해당 유저가 4개의 Note 등록
     */
    @Bean
    public void initDefaultUserAndNotes() {
        // User 등록

        UserRegisterDto userDto = new UserRegisterDto("userA", "password");
        User user = userService.signup(userDto);

        // Note 등록
        NoteRegisterDto noteA = new NoteRegisterDto(user, "UserA Note #1", "Note Number 1.");
        noteService.saveNote(noteA);

        NoteRegisterDto noteB = new NoteRegisterDto(user, "UserA Note #2", "Note Number 2.");
        noteService.saveNote(noteB);

        NoteRegisterDto noteC = new NoteRegisterDto(user, "UserA Note #3", "Note Number 3.");
        noteService.saveNote(noteC);

        NoteRegisterDto noteD = new NoteRegisterDto(user, "UserA Note #4", "Note Number 4.");
        noteService.saveNote(noteD);

    }

    @Bean
    public void initDefaultAdminAndNotice() {
        // Admin 등록
        AdminRegisterDto admin = new AdminRegisterDto("adminA", "password", "adminIdA");
        adminService.saveAdmin(admin);

        // Notice 등록
        noticeService.saveNotice("Welcome", "First Notice");
        noticeService.saveNotice("Instructions", "How to Write Notes: \n1. Log in \n2. Write Notes");
    }
}
