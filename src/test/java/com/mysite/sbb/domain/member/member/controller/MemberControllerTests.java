package com.mysite.sbb.domain.member.member.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

// 022-06: 새 파일. 회원 화면 테스트
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("GET /members/new 는 회원가입 폼을 보여 준다")
    void showJoin() throws Exception {
        mvc.perform(get("/members/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("member_join"))
                .andExpect(content().string(containsString("<title>회원가입 | SBB</title>")))
                .andExpect(content().string(containsString("action=\"/members\"")))
                .andExpect(content().string(containsString("name=\"password1\"")))
                .andExpect(content().string(containsString("type=\"hidden\" name=\"_csrf\"")));
    }
}
