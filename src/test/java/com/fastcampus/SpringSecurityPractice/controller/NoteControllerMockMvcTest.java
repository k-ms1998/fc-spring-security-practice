package com.fastcampus.SpringSecurityPractice.controller;

import com.fastcampus.SpringSecurityPractice.domain.admin.Admin;
import com.fastcampus.SpringSecurityPractice.domain.user.User;
import com.fastcampus.SpringSecurityPractice.repository.AdminRepository;
import com.fastcampus.SpringSecurityPractice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class NoteControllerMockMvcTest {

    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;
    
    /**
     * MockMvc 주입
     * @param webApplicationContext
     */
    @BeforeEach
    public void setUp(@Autowired WebApplicationContext webApplicationContext) {
        
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();

        userRepository.save(User.of("MockUser", "mock", "ROLE_USER")); // Mock User 를 DB에 미리 저장
        adminRepository.save(Admin.of("MockAdmin", "mock", "ROLE_ADMIN", "mockId")); // Mock Admin 을 DB에 미리 저장
    }

    @Test
    void givenNoUser_whenRequestGetNote_thenRedirectToLogin() throws Exception{
        // Given


        // When & Then
         mvc.perform(get("/note"))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithUserDetails(
            value = "MockUser",
            userDetailsServiceBeanName = "userDetailsService", // SpringSecurityConfig 에서 UserDetailsService 를 구현한 메소드 명을 주입,
            setupBefore = TestExecutionEvent.TEST_EXECUTION // 테스트 실행 직전에 Mock User 생성
    )
    void givenUser_whenRequestGetNote_thenRenderNotes() throws Exception{
        // Given


        // When & Then
        mvc.perform(get("/note"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(
            value = "MockAdmin",
            userDetailsServiceBeanName = "userDetailsService", // SpringSecurityConfig 에서 UserDetailsService 를 구현한 메소드 명을 주입,
            setupBefore = TestExecutionEvent.TEST_EXECUTION // 테스트 실행 직전에 Mock User 생성
    )
    void givenAdmin_whenRequestGetNote_thenForbidden() throws Exception{
        // Given

        // When & Then
        /**
         * ADMIN 이라서 ROLE_ADMIN 이면 note 를 조회 하는 것이 막혀 있기 때문에 isForbidden 반환
         */
        mvc.perform(get("/note"))
                .andExpect(status().isForbidden());
    }

}