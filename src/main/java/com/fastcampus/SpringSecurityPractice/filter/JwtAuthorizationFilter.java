package com.fastcampus.SpringSecurityPractice.filter;

import com.fastcampus.SpringSecurityPractice.domain.user.User;
import com.fastcampus.SpringSecurityPractice.jwt.JwtProperties;
import com.fastcampus.SpringSecurityPractice.jwt.JwtUtils;
import com.fastcampus.SpringSecurityPractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * JWT 를 이용한 인증
 *
 * 1. Cookie 에서 JWT Token 을 구합니다.
 * 2. JWT Token 을 파싱하여 username 을 구합니다.
 * 3. username 으로 User 를 구하고 Authentication 을 생성합니다.
 * 4. 생성된 Authentication 을 SecurityContext 에 넣습니다.
 * 5. Exception 이 발생하면 응답의 쿠키를 null 로 변경합니다.
 */
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        try{
            /**
             * 1. Cookie 에서 JWT Token 가져오기
             * -> Request 에서 모든 쿠기들 중, 쿠키 이름이 COOKIE_NAME 이랑 일치하는 쿠키 찾기 -> 해당 쿠키에 토큰이 있기 때문에
             * -> 찾은 쿠키를 map 으로 쿠키의 value 가져오기 -> Value 가 토큰이기 때문에
            */
            token = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals(JwtProperties.COOKIE_NAME))
                    .findFirst()
                    .map(c -> c.getValue())
                    .orElse(null); // 쿠키에 토큰이 없으면 null 반환
        } catch(Exception e){
        }

        if (token != null) {
            try {
                /**
                 * 2. JWT Token 을 파싱하여 username 을 구하기
                 */
                String username = JwtUtils.getUsernameFromToken(token);

                /**
                 * 3. username 으로 User 를 구하고 Authentication 을 생성하기
                 */
                if (username != null) {
                    User user = userRepository.findByUsername(username);
                    if (user != null) {
                        Authentication authentication
                                = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                /**
                 * 실패하는 경우에는 쿠키를 초기화
                 */
                Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        filterChain.doFilter(request, response);
    }
}
