package com.mysite.sbb.domain.question.question.controller;

import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.domain.question.question.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

// 007-03: 새 파일. 서버를 띄우지 않고 가짜 요청(MockMvc)으로 컨트롤러와 템플릿 결과를 확인한다
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class QuestionControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private QuestionRepository questionRepository;

    private Question q1;

    @BeforeEach
    void setUp() {
        q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.addAnswer("스프링부트 게시판입니다.");
        questionRepository.save(q1);
    }

    // 008-01: 루트 URL 리다이렉트 확인
    @Test
    @DisplayName("/ 로 들어오면 질문 목록으로 리다이렉트된다")
    void root() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/question/list"));
    }

    @Test
    @DisplayName("질문 목록 화면에 저장된 질문 제목이 보인다")
    void list() throws Exception {
        mvc.perform(get("/question/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("question_list"))
                .andExpect(content().string(containsString("sbb가 무엇인가요?")))
                // 010-04: 목록의 제목이 상세 링크인지 확인
                .andExpect(content().string(containsString("href=\"/question/detail/" + q1.getId() + "\"")));
    }

    // 010-03: 상세 화면과 없는 질문의 404
    @Test
    @DisplayName("질문 상세 화면에 제목과 내용이 보이고, 없는 질문은 404")
    void detail() throws Exception {
        mvc.perform(get("/question/detail/" + q1.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("question_detail"))
                .andExpect(content().string(containsString("sbb에 대해서 알고 싶습니다.")));

        mvc.perform(get("/question/detail/999999"))
                .andExpect(status().isNotFound());
    }
}
