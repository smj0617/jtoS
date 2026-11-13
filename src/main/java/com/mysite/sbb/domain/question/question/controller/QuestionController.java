package com.mysite.sbb.domain.question.question.controller;

import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// 007-03: 새 파일. 질문 화면을 담당하는 컨트롤러 (책 2-07)
//         @RequiredArgsConstructor → final 필드를 받는 생성자를 Lombok이 만들고, 스프링이 그 생성자로 빈을 주입한다
@Controller
@RequiredArgsConstructor
public class QuestionController {
    // 009-02: 리포지터리 대신 서비스를 주입받는다 → 컨트롤러는 DB를 직접 모르고, 처리는 서비스에 맡긴다
    private final QuestionService questionService;

    // 007-03: @ResponseBody 가 없으므로 반환한 문자열은 템플릿 이름 → templates/question_list.html 을 그린다
    //         Model 에 담은 값은 템플릿에서 ${questionList} 로 꺼내 쓴다
    @GetMapping("/question/list")
    public String list(Model model) {
        // 009-02: questionRepository.findAll() → questionService.getList()
        List<Question> questionList = questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    // 010-03: 질문 상세 화면 (책 2-10)
    //         {id} → 주소의 그 자리 값을 @PathVariable 로 받는다. /question/detail/3 이면 id = 3
    //         없는 id면 getQuestion 이 DataNotFoundException 을 던져 404 가 된다
    @GetMapping("/question/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
}
