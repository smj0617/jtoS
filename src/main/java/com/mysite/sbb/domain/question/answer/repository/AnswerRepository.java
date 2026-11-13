// 006-03: 답변은 질문의 하위 도메인 → domain.question.answer.repository
package com.mysite.sbb.domain.question.answer.repository;

import com.mysite.sbb.domain.question.answer.entity.Answer;

import org.springframework.data.jpa.repository.JpaRepository;

// 005-01: 새 파일. 답변 테이블을 다루는 리포지터리 (책 2-05)
//         답변 저장은 Question.addAnswer + cascade PERSIST 로 하지만, 답변을 id로 직접 조회할 때를 위해 둔다
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
