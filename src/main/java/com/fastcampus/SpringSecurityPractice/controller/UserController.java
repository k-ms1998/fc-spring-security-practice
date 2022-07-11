package com.fastcampus.SpringSecurityPractice.controller;

import com.fastcampus.SpringSecurityPractice.domain.user.User;
import com.fastcampus.SpringSecurityPractice.domain.user.UserRegisterDto;
import com.fastcampus.SpringSecurityPractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * 회원등록 페이지 랜더링
     *
     * @return
     */
    @GetMapping("/signup")
    public String userSignUpPage() {
        return "signup";
    }

    /**
     * 유저 등록
     * 유저 등록 후, 로그인 페이지로 Redirect
     */
    @PostMapping("/signup")
    public String userSignUp(@RequestBody UserRegisterDto userRegisterDto) {
        userService.signup(userRegisterDto);

        return "redirect:login";
    }
}
