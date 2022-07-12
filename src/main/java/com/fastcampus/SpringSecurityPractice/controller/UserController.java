package com.fastcampus.SpringSecurityPractice.controller;

import com.fastcampus.SpringSecurityPractice.domain.user.User;
import com.fastcampus.SpringSecurityPractice.domain.user.UserRegisterDto;
import com.fastcampus.SpringSecurityPractice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/user")
@Slf4j
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
    public String userSignUp(@ModelAttribute UserRegisterDto userRegisterDto) {
        log.info("USER SIGNUP!!!!");
        System.out.println("userRegisterDto = " + userRegisterDto);
        userService.signup(userRegisterDto);

        return "redirect:login";
    }
}
