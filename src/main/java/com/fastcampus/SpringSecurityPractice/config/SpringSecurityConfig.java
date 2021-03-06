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
        http.httpBasic().disable(); // basic authentication filter ????????????

        // csrf
        http.csrf();

        // remember-me
        http.rememberMe();

        // stateless ( session X; cookie O)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // JWT filter
        /**
         * 1. ????????? ??????
         * 2. JwtAuthenticationFilter ??????
         *      -> ????????? ??????: Token ?????? -> 3????????? ??????
         * 3. JwtAuthorizationFilter ??????
         */
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()),
                                     UsernamePasswordAuthenticationFilter.class
        ).addFilterBefore(new JwtAuthorizationFilter(userRepository),
                                     BasicAuthenticationFilter.class);


        // authorization
        http.authorizeRequests()
                // /??? /home??? ???????????? ??????
                .antMatchers("/", "/home", "/signup").permitAll()
                // hello ???????????? USER ?????? ?????? ??????????????? ??????
                .antMatchers("/note").hasRole("USER")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/notice").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/notice").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/notice").hasRole("ADMIN")
                .anyRequest().authenticated();

        // login
        http.formLogin()
                .loginPage("/login") // ????????? ??????????????? FORM ???????????? ?????? URL; th:action="@{/my/login}"
                .defaultSuccessUrl("/")
                .permitAll(); // ?????? ??????

        // logout
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies(JwtProperties.COOKIE_NAME);
    }

    @Override
    public void configure(WebSecurity web) {
        // ?????? ????????? spring security ???????????? ??????
//        web.ignoring().antMatchers("/images/**", "/css/**"); // ?????? ????????? ?????? ???????????????.
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
     * UserDetailsService ??????
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
