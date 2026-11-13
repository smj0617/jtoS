package com.mysite.sbb.domain.member.member.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

// 022-05: 새 파일. 가입 폼 검증 규칙만 따로 확인 (스프링 없이 Validator 로 바로 검사해서 빠르다)
class MemberJoinFormTests {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private Set<String> errorFields(MemberJoinForm form) {
        Set<ConstraintViolation<MemberJoinForm>> violations = validator.validate(form);
        return violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());
    }

    @Test
    @DisplayName("올바른 입력은 오류가 없다")
    void valid() {
        assertThat(errorFields(new MemberJoinForm("user1", "1234", "1234", "user1@test.com"))).isEmpty();
    }

    @Test
    @DisplayName("아이디가 2자, 이메일 형식이 아니면 해당 필드에 오류가 난다")
    void invalid() {
        assertThat(errorFields(new MemberJoinForm("ab", "1234", "1234", "not-email")))
                .containsExactlyInAnyOrder("username", "email");
    }

    @Test
    @DisplayName("비밀번호·비밀번호 확인이 비어 있으면 오류가 난다")
    void blankPassword() {
        assertThat(errorFields(new MemberJoinForm("user1", "", " ", "user1@test.com")))
                .containsExactlyInAnyOrder("password1", "password2");
    }
}
