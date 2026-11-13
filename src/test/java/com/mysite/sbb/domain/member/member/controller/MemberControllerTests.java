package com.mysite.sbb.domain.member.member.controller;

import com.mysite.sbb.domain.member.member.repository.MemberRepository;
import com.mysite.sbb.domain.member.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("GET /members/new 는 회원가입 폼을 보여 준다")
    void showJoin() throws Exception {
        mvc.perform(get("/members/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("member_join"))
                .andExpect(content().string(containsString("<title>회원가입 | SBB</title>")))
                .andExpect(content().string(containsString("action=\"/members\"")))
                .andExpect(content().string(containsString("name=\"password1\"")))
                .andExpect(content().string(containsString("type=\"hidden\" name=\"_csrf\"")))
                // 022-08: 내비게이션 바에 회원가입 메뉴
                .andExpect(content().string(containsString("href=\"/members/new\">회원가입</a>")));
    }

    // 022-07: 가입 처리
    @Test
    @DisplayName("올바른 입력이면 회원이 저장되고 첫 화면으로 리다이렉트된다")
    void join() throws Exception {
        mvc.perform(post("/members").with(csrf())
                        .param("username", "user1").param("password1", "1234")
                        .param("password2", "1234").param("email", "user1@test.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        assertThat(memberRepository.findByUsername("user1")).isPresent();
    }

    @Test
    @DisplayName("두 비밀번호가 다르면 비밀번호 확인 칸에 오류를 보여 주고 저장하지 않는다")
    void joinPasswordMismatch() throws Exception {
        mvc.perform(post("/members").with(csrf())
                        .param("username", "user1").param("password1", "1234")
                        .param("password2", "9999").param("email", "user1@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("member_join"))
                .andExpect(content().string(containsString("2개의 비밀번호가 일치하지 않습니다.")));

        assertThat(memberRepository.findByUsername("user1")).isEmpty();
    }

    @Test
    @DisplayName("이미 있는 아이디로 가입하면 폼 위에 글로벌 오류를 보여 준다")
    void joinDuplicate() throws Exception {
        memberService.join("user1", "first@test.com", "1234");

        mvc.perform(post("/members").with(csrf())
                        .param("username", "user1").param("password1", "1234")
                        .param("password2", "1234").param("email", "second@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("member_join"))
                .andExpect(content().string(containsString("이미 등록된 아이디 또는 이메일입니다.")))
                .andExpect(content().string(containsString("alert alert-danger")));
    }
}
