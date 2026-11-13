// 006-02: 테스트도 질문 리포지터리와 같은 패키지로 이동
package com.mysite.sbb.domain.question.question.repository;

import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.Answer;
import com.mysite.sbb.AnswerRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// 005-01: 새 파일. 책 2-05의 리포지터리 테스트를 하나로 모았다
//         @Transactional → 테스트 메서드마다 끝나면 롤백 → 테스트끼리 데이터가 섞이지 않는다 (강사식)
//         책은 테스트가 DB에 남긴 데이터 때문에 id 값을 맞춰 가며 고쳐야 했는데, 롤백하면 그럴 필요가 없다
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class QuestionRepositoryTests {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    // 005-01: 테스트용 질문 하나를 저장해 돌려준다
    private Question saveQuestion(String subject, String content) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        return questionRepository.save(q);
    }

    @Test
    @DisplayName("저장하면 id와 생성·수정 일시가 자동으로 채워진다")
    void save() {
        Question q = saveQuestion("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");

        assertThat(q.getId()).isNotNull();
        // 005-01: 004-02 BaseEntity의 Auditing 동작 확인
        assertThat(q.getCreateDate()).isNotNull();
        assertThat(q.getModifyDate()).isNotNull();
    }

    @Test
    @DisplayName("findAll, findById")
    void find() {
        Question q1 = saveQuestion("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        saveQuestion("스프링부트 모델 질문입니다.", "id는 자동으로 생성되나요?");

        List<Question> all = questionRepository.findAll();
        assertThat(all).hasSize(2);
        assertThat(questionRepository.findById(q1.getId())).get()
                .extracting(Question::getSubject).isEqualTo("sbb가 무엇인가요?");
    }

    @Test
    @DisplayName("메서드 이름으로 만든 조회: findBySubject, findBySubjectAndContent, findBySubjectLike")
    void findByName() {
        Question q1 = saveQuestion("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");

        assertThat(questionRepository.findBySubject("sbb가 무엇인가요?")).get()
                .extracting(Question::getId).isEqualTo(q1.getId());
        assertThat(questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.")).isPresent();
        assertThat(questionRepository.findBySubjectLike("sbb%")).hasSize(1);
    }

    @Test
    @DisplayName("수정과 삭제")
    void modifyAndDelete() {
        Question q = saveQuestion("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");

        q.setSubject("수정된 제목");
        questionRepository.flush();
        assertThat(questionRepository.findById(q.getId()).get().getSubject()).isEqualTo("수정된 제목");

        questionRepository.delete(q);
        assertThat(questionRepository.count()).isZero();
    }

    @Test
    @DisplayName("addAnswer 후 질문만 저장해도 답변이 함께 저장된다 (004-03 cascade PERSIST)")
    void addAnswer() {
        Question q = saveQuestion("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");

        Answer a = q.addAnswer("네 자동으로 생성됩니다.");
        questionRepository.flush();

        assertThat(a.getId()).isNotNull();
        assertThat(answerRepository.findById(a.getId())).get()
                .extracting(answer -> answer.getQuestion().getId()).isEqualTo(q.getId());
        assertThat(q.getAnswerList()).hasSize(1);
    }
}
