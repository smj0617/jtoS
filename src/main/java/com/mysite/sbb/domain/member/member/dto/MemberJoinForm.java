package com.mysite.sbb.domain.member.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// 022-05: 새 파일. 회원가입 폼 입력값 (책 3-06의 UserCreateForm). 016-05 처럼 record 로 만든다
public record MemberJoinForm(
        // 022-05: 아이디 3~25자. 25 는 Member.username 컬럼 길이(022-01)와 맞춘다
        @NotBlank(message = "아이디는 필수항목입니다.")
        @Size(min = 3, max = 25, message = "아이디는 3자 이상 25자 이하로 입력하세요.")
        String username,

        @NotBlank(message = "비밀번호는 필수항목입니다.")
        String password1,

        // 022-05: 비밀번호 확인. 두 값이 같은지는 필드 하나만 보는 어노테이션으로 검사할 수 없어 컨트롤러(022-07)에서 비교한다
        @NotBlank(message = "비밀번호 확인은 필수항목입니다.")
        String password2,

        // 022-05: @Email → 이메일 형식(@ 포함 등)이 아니면 실패
        @NotBlank(message = "이메일은 필수항목입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        String email
) {
}
