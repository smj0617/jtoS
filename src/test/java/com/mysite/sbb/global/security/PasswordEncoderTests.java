package com.mysite.sbb.global.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

// 022-03: 새 파일. 비밀번호 암호화 도구 동작 확인
@ActiveProfiles("test")
@SpringBootTest
class PasswordEncoderTests {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("BCrypt 는 같은 비밀번호도 매번 다르게 암호화하고, matches 로 원래 비밀번호인지 확인한다")
    void bcrypt() {
        String a = passwordEncoder.encode("1234");
        String b = passwordEncoder.encode("1234");

        assertThat(a).startsWith("$2a$").isNotEqualTo("1234").isNotEqualTo(b);
        assertThat(passwordEncoder.matches("1234", a)).isTrue();
        assertThat(passwordEncoder.matches("4321", a)).isFalse();
    }
}
