package com.mysite.sbb.domain.question.answer.controller;

import com.mysite.sbb.domain.member.member.entity.Member;
import com.mysite.sbb.domain.member.member.service.MemberService;
import com.mysite.sbb.domain.question.answer.dto.AnswerForm;
import com.mysite.sbb.domain.question.answer.entity.Answer;
import com.mysite.sbb.domain.question.answer.service.AnswerService;
import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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
    // 024-06: 로그인한 사용자만 답변 등록
    @PreAuthorize("isAuthenticated()")
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

    // 025-09: 답변 수정 화면 GET /questions/{questionId}/answers/{id}/edit (책 3-09의 GET /answer/modify/{id})
    //         글쓴이 본인만. 기존 내용을 AnswerForm 에 담아 보여 준다
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/edit")
    public String showModify(@PathVariable Integer questionId, @PathVariable Integer id, Model model, Principal principal) {
        Answer answer = answerService.getAnswer(id);
        checkAuthor(answer, principal);
        model.addAttribute("answerForm", new AnswerForm(answer.getContent()));
        model.addAttribute("questionId", questionId);
        model.addAttribute("answerId", id);
        return "answer_modify";
    }

    // 025-09: 답변 수정 처리 PUT /questions/{questionId}/answers/{id} (수정 화면 폼의 POST + _method=put)
    //         성공하면 그 질문 상세로 돌아간다
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public String modify(@PathVariable Integer questionId, @PathVariable Integer id, @Valid AnswerForm answerForm,
                         BindingResult bindingResult, Model model, Principal principal) {
        Answer answer = answerService.getAnswer(id);
        checkAuthor(answer, principal);
        if (bindingResult.hasErrors()) {
            model.addAttribute("questionId", questionId);
            model.addAttribute("answerId", id);
            return "answer_modify";
        }
        answerService.modify(answer, answerForm.content());
        return "redirect:/questions/%d".formatted(questionId);
    }

    // 025-09: 답변 글쓴이 본인인지 확인. 아니면 403 (025-03 질문과 같은 규칙)
    private void checkAuthor(Answer answer, Principal principal) {
        if (answer.getAuthor() == null || !answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정·삭제 권한이 없습니다.");
        }
    }
}