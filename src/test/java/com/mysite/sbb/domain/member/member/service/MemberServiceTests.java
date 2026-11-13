package com.mysite.sbb.domain.member.member.service;

import com.mysite.sbb.domain.member.member.entity.Member;
import com.mysite.sbb.global.exception.DataNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// 022-04: 새 파일. 회원가입 서비스 테스트
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class MemberServiceTests {
    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("join 은 비밀번호를 암호화해서 저장한다")
    void join() {
        Member m = memberService.join("user1", "user1@test.com", "1234");

        assertThat(m.getId()).isNotNull();
        assertThat(m.getCreateDate()).isNotNull();
        assertThat(m.getPassword()).isNotEqualTo("1234");
        assertThat(passwordEncoder.matches("1234", m.getPassword())).isTrue();
    }

    // 024-03: 아이디로 회원 찾기
    @Test
    @DisplayName("getMember 는 아이디로 회원을 찾고, 없으면 DataNotFoundException")
    void getMember() {
        memberService.join("user1", "user1@test.com", "1234");

        assertThat(memberService.getMember("user1").getEmail()).isEqualTo("user1@test.com");
        assertThatThrownBy(() -> memberService.getMember("nobody"))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("nobody 회원이 없습니다.");
    }
}
