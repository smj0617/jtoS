package com.mysite.sbb.domain.member.member.controller;

import com.mysite.sbb.domain.member.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

    // 023-04: 로그아웃
    @Test
    @DisplayName("로그인 후 POST /members/logout 하면 같은 세션이 로그아웃되고 첫 화면으로 이동한다")
    void logoutSuccess() throws Exception {
        // 023-04: 실제로 로그인해서 세션을 받은 뒤, 그 세션으로 로그아웃한다
        MockHttpSession session = (MockHttpSession) mvc.perform(formLogin("/members/login").user("user1").password("1234"))
                .andExpect(authenticated())
                .andReturn().getRequest().getSession();

        mvc.perform(post("/members/logout").session(session).with(csrf()))
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("GET /members/logout 으로는 로그아웃되지 않는다")
    void logoutByGetIsNotAllowed() throws Exception {
        mvc.perform(get("/members/logout").with(user("user1")))
                .andExpect(status().isNotFound());
    }
}
