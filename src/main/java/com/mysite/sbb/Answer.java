package com.mysite.sbb;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 004-01: 새 파일. 책 2-04 그대로의 답변 엔티티 → answer 테이블
@Getter
@Setter
@Entity
public class Answer {
    // 004-01: 기본키(PK). DB가 번호를 자동으로 매긴다
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 004-01: 답변 내용 → TEXT 타입
    @Column(columnDefinition = "TEXT")
    private String content;

    // 004-01: 작성 일시 → create_date 컬럼
    private LocalDateTime createDate;

    // 004-01: 어느 질문의 답변인지. 답변 여러 개(Many)가 질문 하나(One)에 달린다
    //         → answer 테이블에 question_id 외래키(FK) 컬럼이 생긴다
    //         기본값은 즉시 로딩(EAGER)이라 답변을 읽을 때 질문도 항상 같이 읽는다. 004-03에서 LAZY로 바꾼다
    @ManyToOne
    private Question question;
}
