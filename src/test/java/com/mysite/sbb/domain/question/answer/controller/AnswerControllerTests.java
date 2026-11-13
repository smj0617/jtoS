package com.mysite.sbb.domain.question.answer.controller;

import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.domain.question.question.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

// 012-02: 새 파일. 답변 등록 요청 테스트
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AnswerControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("POST /questions/{id}/answers 는 답변을 저장하고 상세로 리다이렉트한다")
    void create() throws Exception {
        Question q = new Question();
        q.setSubject("질문");
        q.setContent("내용");
        questionRepository.save(q);

        mvc.perform(post("/questions/%d/answers".formatted(q.getId()))
                        .param("content", "답변입니다."))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/questions/%d".formatted(q.getId())));

        assertThat(q.getAnswerList()).extracting("content").containsExactly("답변입니다.");
    }

    // 016-09: 답변 서버 검증 실패
    @Test
    @DisplayName("답변 내용이 비어 있으면 저장하지 않고 상세 화면을 다시 보여 준다")
    void createInvalid() throws Exception {
        Question q = new Question();
        q.setSubject("질문");
        q.setContent("내용");
        questionRepository.save(q);

        mvc.perform(post("/questions/%d/answers".formatted(q.getId()))
                        .param("content", " "))
                .andExpect(status().isOk())
                .andExpect(view().name("question_detail"))
                // 016-10: 상세 화면 답변 폼에 오류 메시지와 빨간 테두리가 보이는지 확인
                .andExpect(content().string(containsString("내용은 필수항목입니다.")))
                .andExpect(content().string(containsString("is-invalid")));

        assertThat(q.getAnswerList()).isEmpty();
    }
}
