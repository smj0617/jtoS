package com.mysite.sbb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// 005-01: 새 파일. 질문 테이블을 다루는 리포지터리 (책 2-05)
//         JpaRepository<엔티티, PK 타입> 을 상속만 하면 save, findAll, findById, delete, count 등이 자동으로 생긴다
//         인터페이스뿐이지만 Spring Data JPA가 실행할 때 구현 객체를 만들어 빈으로 등록한다
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // 005-01: 메서드 이름만으로 쿼리가 만들어진다 → where subject = ?
    Optional<Question> findBySubject(String subject);

    // 005-01: where subject = ? and content = ?
    Optional<Question> findBySubjectAndContent(String subject, String content);

    // 005-01: where subject like ? (% 는 호출하는 쪽에서 붙인다)
    List<Question> findBySubjectLike(String subject);
}
