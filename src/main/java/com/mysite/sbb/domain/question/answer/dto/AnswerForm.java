package com.mysite.sbb.domain.question.answer.dto;

import jakarta.validation.constraints.NotBlank;

// 016-09: 새 파일. 답변 폼의 입력값 (책 2-16의 AnswerForm). 016-05 QuestionForm 과 같은 record 방식
public record AnswerForm(
        @NotBlank(message = "내용은 필수항목입니다.")
        String content
) {
}
