package com.mysite.sbb.domain.question.question.controller;

import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
        List<Question> questionList = questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    // 016-02: 질문 등록 폼 화면 (책 2-16)
    //         REST 규칙: 새 자원을 만드는 입력 화면은 GET /questions/new (책은 GET /question/create)
    //         /{id} 보다 위에 두지 않아도 스프링은 글자 그대로인 /new 를 {id} 보다 먼저 맞춘다
    @GetMapping("/new")
    public String showCreate() {
        return "question_form";
    }

    // 016-03: 질문 저장 (책 2-16)
    //         REST 규칙: 같은 자원 주소에 POST → POST /questions = 질문 하나 생성
    //         폼의 name="subject", name="content" 값을 @RequestParam 으로 받는다 (016-05 에서 폼 객체로 바꾼다)
    //         저장 후 목록으로 redirect → 새로고침해도 다시 저장되지 않는다 (012-02 PRG 와 같다)
    @PostMapping
    public String create(@RequestParam String subject, @RequestParam String content) {
        questionService.create(subject, content);
        return "redirect:/questions";
    }

    // 010-03: 질문 상세 화면 (책 2-10)
    //         {id} → 주소의 그 자리 값을 @PathVariable 로 받는다. /question/detail/3 이면 id = 3
    //         없는 id면 getQuestion 이 DataNotFoundException 을 던져 404 가 된다
    // 011-01: "/question/detail/{id}" → "/detail/{id}"
    // 011-02: GET /questions/{id} → 질문 하나
    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
}
