package com.mysite.sbb.domain.question.answer.controller;

import com.mysite.sbb.domain.question.answer.service.AnswerService;
import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 012-02: 새 파일. 답변 요청을 받는 컨트롤러 (책 2-12)
//         강사식 REST 주소: 답변은 질문에 속한 자원이라 /questions/{questionId}/answers 아래에 둔다
//         (책은 POST /answer/create/{id})
@Controller
@RequiredArgsConstructor
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    // 012-02: POST /questions/{questionId}/answers → 답변 생성
    //         @RequestParam content → 폼의 name="content" 입력값을 받는다
    //         저장 후 화면을 그리지 않고 상세 주소로 redirect → 새로고침해도 같은 답변이 두 번 등록되지 않는다 (PRG 패턴)
    @PostMapping
    public String create(@PathVariable Integer questionId, @RequestParam String content) {
        Question question = questionService.getQuestion(questionId);
        answerService.create(question, content);
        return "redirect:/questions/%d".formatted(questionId);
    }
}
