package com.mysite.sbb.domain.question.question.controller;

import com.mysite.sbb.domain.question.answer.dto.AnswerForm;
import com.mysite.sbb.domain.question.question.dto.QuestionForm;
import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 007-03: 새 파일. 질문 화면을 담당하는 컨트롤러 (책 2-07)
//         @RequiredArgsConstructor → final 필드를 받는 생성자를 Lombok이 만들고, 스프링이 그 생성자로 빈을 주입한다
// 011-01: 클래스에 공통 주소 /question 을 붙였다 (책 2-11)
//         → 메서드의 주소는 이 뒤에 이어진다. 주소가 바뀌어도 이 한 줄만 고치면 된다
// 011-02: 강사식 REST 주소. 자원 이름은 복수형 /questions, 동작은 주소가 아니라 HTTP 메서드(GET/POST...)로 구분한다
@RequestMapping("/questions")
@Controller
@RequiredArgsConstructor
public class QuestionController {
    // 009-02: 리포지터리 대신 서비스를 주입받는다 → 컨트롤러는 DB를 직접 모르고, 처리는 서비스에 맡긴다
    private final QuestionService questionService;

    // 007-03: @ResponseBody 가 없으므로 반환한 문자열은 템플릿 이름 → templates/question_list.html 을 그린다
    //         Model 에 담은 값은 템플릿에서 ${questionList} 로 꺼내 쓴다
    // 011-01: "/question/list" → "/list" (앞부분은 클래스의 @RequestMapping 이 붙인다)
    // 011-02: GET /questions → 질문 목록 (/list 같은 동사·명사 꼬리를 붙이지 않는다)
    @GetMapping
    public String list(Model model) {
        // 009-02: questionRepository.findAll() → questionService.getList()
        // 018-01: 서비스가 Page 를 돌려준다. 우선 첫 페이지(0)만 보여 준다 (page 값 받기는 018-03)
        //         Page 도 반복할 수 있어서 템플릿의 th:each 는 그대로 동작한다
        Page<Question> questionList = questionService.getList(0);
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    // 016-02: 질문 등록 폼 화면 (책 2-16)
    //         REST 규칙: 새 자원을 만드는 입력 화면은 GET /questions/new (책은 GET /question/create)
    //         /{id} 보다 위에 두지 않아도 스프링은 글자 그대로인 /new 를 {id} 보다 먼저 맞춘다
    // 016-06: 빈 QuestionForm 을 받아 두면 모델에 "questionForm" 이름으로 담긴다
    //         → 템플릿의 th:object="${questionForm}" 이 처음 열 때도 쓸 객체가 생긴다 (값은 모두 null)
    @GetMapping("/new")
    public String showCreate(QuestionForm questionForm) {
        return "question_form";
    }

    // 016-03: 질문 저장 (책 2-16)
    //         REST 규칙: 같은 자원 주소에 POST → POST /questions = 질문 하나 생성
    //         폼의 name="subject", name="content" 값을 @RequestParam 으로 받는다 (016-05 에서 폼 객체로 바꾼다)
    //         저장 후 목록으로 redirect → 새로고침해도 다시 저장되지 않는다 (012-02 PRG 와 같다)
    // 016-05: @RequestParam 두 개 → 폼 객체(QuestionForm record) 하나로 받는다
    //         @Valid → QuestionForm 의 @NotBlank, @Size 규칙으로 검사한다
    //         BindingResult → 검사 결과. 반드시 @Valid 객체 바로 뒤에 둬야 한다 (없으면 검증 실패 시 400 오류로 끝난다)
    //         오류가 있으면 저장하지 않고 등록 폼을 다시 보여 준다 (오류 메시지 표시는 016-06)
    @PostMapping
    public String create(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        questionService.create(questionForm.subject(), questionForm.content());
        return "redirect:/questions";
    }

    // 010-03: 질문 상세 화면 (책 2-10)
    //         {id} → 주소의 그 자리 값을 @PathVariable 로 받는다. /question/detail/3 이면 id = 3
    //         없는 id면 getQuestion 이 DataNotFoundException 을 던져 404 가 된다
    // 011-01: "/question/detail/{id}" → "/detail/{id}"
    // 011-02: GET /questions/{id} → 질문 하나
    // 016-10: 빈 AnswerForm 을 받아 모델에 담는다 → 상세 화면 답변 폼의 th:object="${answerForm}" 이 쓸 객체
    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, AnswerForm answerForm, Model model) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
}
