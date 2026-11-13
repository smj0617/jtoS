package com.mysite.sbb.domain.question.question.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// 016-05: 새 파일. 질문 등록 폼의 입력값을 담는 객체 (책 2-16의 QuestionForm)
//         강사식: 클래스 + @Getter/@Setter 대신 record 를 쓴다
//         record → 필드·생성자·접근자(subject())·equals 를 자바가 만들어 준다. 값은 만든 뒤 바꿀 수 없다
//         스프링은 폼 값을 생성자로 넣어 record 를 만든다
//         검증 규칙을 필드에 붙여 두면, 컨트롤러의 @Valid 가 이 규칙대로 검사한다
public record QuestionForm(
        // 016-05: null, 빈 문자열, 공백만 있는 값을 막는다
        @NotBlank(message = "제목은 필수항목입니다.")
        // 016-05: 엔티티의 @Column(length = 200) 과 맞춘다. 넘으면 DB 저장 전에 막는다
        @Size(max = 200, message = "제목은 200자 이하로 입력하세요.")
        String subject,

        @NotBlank(message = "내용은 필수항목입니다.")
        String content
) {
}
