package com.mysite.sbb.domain.member.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// 023-01: 새 파일. 로그인용 회원 조회 테스트
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class MemberSecurityServiceTests {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberSecurityService memberSecurityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("아이디로 회원을 찾아 암호화된 비밀번호와 ROLE_MEMBER 권한을 담아 돌려준다")
    void loadUser() {
        memberService.join("user1", "user1@test.com", "1234");

        UserDetails user = memberSecurityService.loadUserByUsername("user1");

        assertThat(user.getUsername()).isEqualTo("user1");
        assertThat(passwordEncoder.matches("1234", user.getPassword())).isTrue();
        assertThat(user.getAuthorities()).extracting(GrantedAuthority::getAuthority).containsExactly("ROLE_MEMBER");
    }

    @Test
    @DisplayName("admin 은 ROLE_ADMIN 도 갖고, 없는 아이디는 UsernameNotFoundException")
    void adminAndNotFound() {
        memberService.join("admin", "admin@test.com", "1234");

        assertThat(memberSecurityService.loadUserByUsername("admin").getAuthorities())
                .extracting(GrantedAuthority::getAuthority).containsExactlyInAnyOrder("ROLE_MEMBER", "ROLE_ADMIN");
        assertThatThrownBy(() -> memberSecurityService.loadUserByUsername("nobody"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
