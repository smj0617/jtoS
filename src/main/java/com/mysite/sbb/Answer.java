package com.mysite.sbb;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

// 004-01: 새 파일. 책 2-04 그대로의 답변 엔티티 → answer 테이블
// 004-02: BaseEntity 상속 → id, createDate, modifyDate 를 물려받는다 (여기 있던 id, createDate 필드는 삭제)
@Getter
@Setter
@Entity
public class Answer extends BaseEntity {
    // 004-01: 답변 내용 → TEXT 타입
    @Column(columnDefinition = "TEXT")
    private String content;

    // 004-01: 어느 질문의 답변인지. 답변 여러 개(Many)가 질문 하나(One)에 달린다
    //         → answer 테이블에 question_id 외래키(FK) 컬럼이 생긴다
    //         기본값은 즉시 로딩(EAGER)이라 답변을 읽을 때 질문도 항상 같이 읽는다. 004-03에서 LAZY로 바꾼다
    @ManyToOne
    private Question question;
}
