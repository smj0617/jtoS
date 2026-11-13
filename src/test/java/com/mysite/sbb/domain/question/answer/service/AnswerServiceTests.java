package com.mysite.sbb.domain.question.answer.service;

import com.mysite.sbb.domain.member.member.entity.Member;
import com.mysite.sbb.domain.member.member.service.MemberService;
import com.mysite.sbb.domain.question.answer.entity.Answer;
import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.domain.question.question.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

// 012-01: 새 파일. AnswerService 테스트
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class AnswerServiceTests {
    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionRepository questionRepository;

    // 024-05: 글쓴이 회원을 만들기 위해
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("create는 답변을 저장하고 질문의 답변 목록에도 넣는다")
    void create() {
        Question q = new Question();
        q.setSubject("질문");
        q.setContent("내용");
        questionRepository.save(q);

        // 024-05: 글쓴이와 함께 저장
        Member author = memberService.join("writer1", "writer1@test.com", "1234");
        Answer a = answerService.create(q, "답변입니다.", author);

        assertThat(a.getId()).isNotNull();
        assertThat(a.getCreateDate()).isNotNull();
        assertThat(a.getQuestion()).isSameAs(q);
        assertThat(a.getAuthor().getUsername()).isEqualTo("writer1");
        assertThat(q.getAnswerList()).containsExactly(a);
    }
}
