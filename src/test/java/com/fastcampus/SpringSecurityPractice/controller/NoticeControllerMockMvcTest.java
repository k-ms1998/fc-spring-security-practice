package com.fastcampus.SpringSecurityPractice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Rollback(value = false)
class NoticeControllerMockMvcTest {

    private MockMvc mvc;

    /**
     * MockMvc 주입
     *
     * @param webApplicationContext
     */
    @BeforeEach
    public void setUp(@Autowired WebApplicationContext webApplicationContext) {

        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
    }

    @Test
    void givenNoticeWithoutLogin_whenRequestingGetNotice_thenRedirectToLoginPage() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/notice"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser
    void givenNoticeWithLogin_whenRequestingGetNotice_thenRenderNoticePage() throws Exception {
        // Given
        /**
         * @WithMockUser 로 임의의 User 로 로그인한 상황이라고 가정
         */

        // When & Then
        mvc.perform(get("/notice"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"}, username = "admin", password = "admin")
    void givenAdmin_whenRequestingPostNotice_thenRenderNoticePage() throws Exception{
        // Given

        // When & Then
        mvc.perform(post("/notice")
                        .with(csrf())
                        .param("title", "Mock Notice Title")
                        .param("content", "Mock Notice Content"))
                        /**
                         * {
                         *     "title" : "Mock Notice Title",
                         *     "content" : "Mock Notice Content"
                         * }
                         * 이렇게 Body를 넘겨주는 것처럼 작동함 -> title과 content가 각각 자동으로 DTO와 매핑되서 처리됨
                         */
                .andExpect(status().is3xxRedirection()) // 성공하면 index로 redirect 되기 때문
                .andExpect(redirectedUrl("notice"));

    }
}