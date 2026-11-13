// 006-02: 질문 도메인으로 이동 → domain.question.question.repository
package com.mysite.sbb.domain.question.question.repository;

import com.mysite.sbb.domain.question.question.entity.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // 029-01: 검색 (책 3-13). 질문 제목·내용·글쓴이, 답변 내용·답변 글쓴이 중 하나라도 검색어를 포함하면 찾는다
    //         @Query → 메서드 이름 규칙으로 표현하기 어려운 조건을 JPQL(엔티티 기준 SQL)로 직접 쓴다
    //         left join → 글쓴이·답변이 없는 질문도 빠지지 않게 한다. 답변이 여러 개면 같은 질문이 여러 줄 나오므로 distinct
    //         Pageable → 018 페이징·정렬이 그대로 적용된다
    @Query("""
            select distinct q
            from Question q
            left join q.author qa
            left join q.answerList a
            left join a.author aa
            where (:kwType = 'subject' and q.subject like %:kw%)
               or (:kwType = 'content' and (q.content like %:kw% or a.content like %:kw%))
               or (:kwType = 'author' and (qa.username like %:kw% or aa.username like %:kw%))
               or (:kwType not in ('subject', 'content', 'author') and (
                       q.subject like %:kw%
                    or q.content like %:kw%
                    or qa.username like %:kw%
                    or a.content like %:kw%
                    or aa.username like %:kw%))
            """)
    // 029-04: 검색 대상(kwType) 추가. subject=제목, content=질문·답변 내용, author=질문·답변 글쓴이, 그 밖(all)=전체(029-01 조건)
    Page<Question> search(@Param("kwType") String kwType, @Param("kw") String kw, Pageable pageable);
}
