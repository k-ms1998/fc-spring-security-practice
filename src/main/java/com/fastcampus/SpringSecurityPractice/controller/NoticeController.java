package com.fastcampus.SpringSecurityPractice.controller;

import com.fastcampus.SpringSecurityPractice.domain.note.NoteRegisterDto;
import com.fastcampus.SpringSecurityPractice.domain.notice.Notice;
import com.fastcampus.SpringSecurityPractice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 공지사항 조회
     *
     * @return notice/index.html
     */
    @GetMapping("/")
    public String getNotice(Model model) {
        List<Notice> notices = noticeService.findAll();
        model.addAttribute("notices", notices);
        return "notice/index";
    }

    /**
     * 공지사항 등록
     */
    @PostMapping("/")
    public String postNotice(@RequestBody NoteRegisterDto noteRegisterDto) {
        String title = noteRegisterDto.getTitle();
        String content = noteRegisterDto.getContent();

        noticeService.saveNotice(title, content);
        return "redirect:notice";
    }

    /**
     * 공지사항 삭제
     *
     * @param id 공지사항 ID
     * @return notice/index.html refresh
     */
    @DeleteMapping
    public String deleteNotice(@RequestParam Long id) {
        noticeService.deleteNotice(id);
        return "redirect:notice";
    }
}
