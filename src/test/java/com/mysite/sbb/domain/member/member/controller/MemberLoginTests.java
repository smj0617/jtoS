package com.mysite.sbb.domain.member.member.controller;

import com.mysite.sbb.domain.member.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

// 023-03: 새 파일. 시큐리티 폼 로그인 테스트
//         formLogin(주소).user().password() → 로그인 폼 POST 를 CSRF 토큰과 함께 흉내 낸다
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberLoginTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService.join("user1", "user1@test.com", "1234");
    }

    @Test
    @DisplayName("맞는 아이디·비밀번호면 로그인되고 첫 화면으로 이동한다")
    void loginSuccess() throws Exception {
        mvc.perform(formLogin("/members/login").user("user1").password("1234"))
                .andExpect(authenticated().withUsername("user1"))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("비밀번호가 틀리면 로그인되지 않고 /members/login?error 로 돌아간다")
    void loginFail() throws Exception {
        mvc.perform(formLogin("/members/login").user("user1").password("9999"))
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/members/login?error"));
    }
}
