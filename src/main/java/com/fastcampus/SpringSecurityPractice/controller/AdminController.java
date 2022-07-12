package com.fastcampus.SpringSecurityPractice.controller;

import com.fastcampus.SpringSecurityPractice.domain.admin.AdminRegisterDto;
import com.fastcampus.SpringSecurityPractice.service.AdminService;
import com.fastcampus.SpringSecurityPractice.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String adminSignUpPage() {
        return "signup";
    }

    @PostMapping
    public String adminSignUp(@RequestBody AdminRegisterDto adminRegisterDto) {
        adminService.saveAdmin(adminRegisterDto);

        return "redirect:login";
    }

}
