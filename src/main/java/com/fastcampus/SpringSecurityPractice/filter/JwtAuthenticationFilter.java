package com.fastcampus.SpringSecurityPractice.filter;

import com.fastcampus.SpringSecurityPractice.domain.user.User;
import com.fastcampus.SpringSecurityPractice.jwt.JwtProperties;
import com.fastcampus.SpringSecurityPractice.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * JWT 를 이용한 로그인 인증
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    /**
     * 로그인 인증 시도
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getParameter("username"), request.getParameter("password"), new ArrayList<>());

        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * 인증에 성공 했을때:
     * JWT Token 을 생성해서 쿠키에 넣는다.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {

        User user = (User) authentication.getPrincipal();
        String token = JwtUtils.createToken(user);

        // 쿠키 생성
        Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, token);
        cookie.setMaxAge(JwtProperties.EXPIRATION_TIME);    // 쿠키 만료 시간 설정
        cookie.setPath("/");

        response.addCookie(cookie);
        response.sendRedirect("/");
    }

    /**
     * 인증에 실패 했을때:
     * 로그인 페이지로 Redirect
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.sendRedirect("/login");
    }
}
