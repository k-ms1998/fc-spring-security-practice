package com.fastcampus.SpringSecurityPractice.config;

import com.fastcampus.SpringSecurityPractice.domain.admin.Admin;
import com.fastcampus.SpringSecurityPractice.domain.user.User;
import com.fastcampus.SpringSecurityPractice.filter.JwtAuthenticationFilter;
import com.fastcampus.SpringSecurityPractice.filter.JwtAuthorizationFilter;
import com.fastcampus.SpringSecurityPractice.filter.StopWatchFilter;
import com.fastcampus.SpringSecurityPractice.jwt.JwtProperties;
import com.fastcampus.SpringSecurityPractice.repository.UserRepository;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AdminService adminService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Add Custom Filters
        http.addFilterBefore(new StopWatchFilter(), WebAsyncManagerIntegrationFilter.class);

        // basic authentication
        http.httpBasic().disable(); // basic authentication filter 비활성화

        // csrf
        http.csrf();

        // remember-me
        http.rememberMe();

        // stateless ( session X; cookie O)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // JWT filter
        /**
         * 1. 로그인 시도
         * 2. JwtAuthenticationFilter 동작
         *      -> 로그인 성공: Token 생성 -> 3번으로 이동
         * 3. JwtAuthorizationFilter 동작
         */
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()),
                                     UsernamePasswordAuthenticationFilter.class
        ).addFilterBefore(new JwtAuthorizationFilter(userRepository),
                                     BasicAuthenticationFilter.class);


        // authorization
        http.authorizeRequests()
                // /와 /home은 모두에게 허용
                .antMatchers("/", "/home", "/signup").permitAll()
                // hello 페이지는 USER 롤을 가진 유저에게만 허용
                .antMatchers("/note").hasRole("USER")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/notice").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/notice").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/notice").hasRole("ADMIN")
                .anyRequest().authenticated();

        // login
        http.formLogin()
                .loginPage("/login") // 로그인 페이지에서 FORM 데이터를 보낼 URL; th:action="@{/my/login}"
                .defaultSuccessUrl("/")
                .permitAll(); // 모두 허용

        // logout
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies(JwtProperties.COOKIE_NAME);
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
            if (username.contains("admin") || username.contains("Admin")) {
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
