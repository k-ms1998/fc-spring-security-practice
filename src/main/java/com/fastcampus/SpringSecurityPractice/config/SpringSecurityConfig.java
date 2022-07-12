package com.fastcampus.SpringSecurityPractice.config;

import com.fastcampus.SpringSecurityPractice.domain.admin.Admin;
import com.fastcampus.SpringSecurityPractice.domain.user.User;
import com.fastcampus.SpringSecurityPractice.service.AdminService;
import com.fastcampus.SpringSecurityPractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final AdminService adminService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // basic authentication
        http.httpBasic().disable(); // basic authentication filter 비활성화

        // csrf
        http.csrf();

        // remember-me
        http.rememberMe();

        // authorization
        http.authorizeRequests()
                // /와 /home은 모두에게 허용
                .antMatchers("/", "/home", "/signup", "/login").permitAll()
                // hello 페이지는 USER 롤을 가진 유저에게만 허용
                .antMatchers("/note").hasRole("USER")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/notice").permitAll()
                .antMatchers(HttpMethod.POST, "/notice").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/notice").hasRole("ADMIN")
                .anyRequest().authenticated();

        // login
        http.formLogin()
                .loginPage("/my/login") // 로그인 페이지에서 FORM 데이터를 보낼 URL; th:action="@{/my/login}"
                .defaultSuccessUrl("/")
                .permitAll(); // 모두 허용

        // logout
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/");
    }

    @Override
    public void configure(WebSecurity web) {
        // 정적 리소스 spring security 대상에서 제외
//        web.ignoring().antMatchers("/images/**", "/css/**"); // 아래 코드와 같은 코드입니다.
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
     * UserDetailsService 구현
     *
     * @return UserDetailsService
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            if (username.contains("admin")) {
                Admin admin = adminService.findByAdminName(username);
                if (admin == null) {
                    throw new UsernameNotFoundException(username);
                }
                return admin;

            } else {
                User user = userService.findByUsername(username);
                if (user == null) {
                    throw new UsernameNotFoundException(username);
                }
                return user;
            }

        };
    }
}
