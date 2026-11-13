package com.mysite.sbb.domain.question.answer.controller;

import com.mysite.sbb.domain.member.member.entity.Member;
import com.mysite.sbb.domain.member.member.service.MemberService;
import com.mysite.sbb.domain.question.answer.dto.AnswerForm;
import com.mysite.sbb.domain.question.answer.service.AnswerService;
import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

// 012-02: 새 파일. 답변 요청을 받는 컨트롤러 (책 2-12)
//         강사식 REST 주소: 답변은 질문에 속한 자원이라 /questions/{questionId}/answers 아래에 둔다
//         (책은 POST /answer/create/{id})
@Controller
@RequiredArgsConstructor
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    // 024-05: 글쓴이 회원을 찾기 위해 주입
    private final MemberService memberService;

    // 012-02: POST /questions/{questionId}/answers → 답변 생성
    //         저장 후 화면을 그리지 않고 상세 주소로 redirect → 새로고침해도 같은 답변이 두 번 등록되지 않는다 (PRG 패턴)
    // 016-09: @RequestParam String content → @Valid AnswerForm + BindingResult (016-05 질문과 같은 방식)
    //         오류가 있으면 저장하지 않고 상세 화면을 다시 그린다. 상세 화면은 question 이 필요하므로 모델에 담는다
    @PostMapping
    public String create(@PathVariable Integer questionId, @Valid AnswerForm answerForm,
                         BindingResult bindingResult, Model model, Principal principal) {
        Question question = questionService.getQuestion(questionId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        // 024-05: 로그인한 아이디로 회원을 찾아 글쓴이로 저장
        Member author = memberService.getMember(principal.getName());
        answerService.create(question, answerForm.content(), author);
        return "redirect:/questions/%d".formatted(questionId);
    }
}
