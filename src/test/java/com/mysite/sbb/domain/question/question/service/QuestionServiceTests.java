package com.mysite.sbb.domain.question.question.service;

import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.domain.question.question.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

// 009-03: 새 파일. 서비스 메서드를 직접 호출해 결과를 확인하는 테스트
//         컨트롤러 테스트(화면)와 따로 두면, 실패했을 때 문제가 화면 쪽인지 처리 쪽인지 바로 구분된다
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class QuestionServiceTests {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    // 009-03: 테스트용 질문 저장 도우미
    private Question saveQuestion(String subject) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(subject + " 내용");
        return questionRepository.save(q);
    }

    @Test
    @DisplayName("getList는 저장된 질문을 모두 돌려준다")
    void getList() {
        saveQuestion("질문1");
        saveQuestion("질문2");

        assertThat(questionService.getList())
                .extracting(Question::getSubject)
                .containsExactly("질문1", "질문2");
    }
}
