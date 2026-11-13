package com.mysite.sbb;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

// 004-01: 새 파일. 책 2-04 그대로의 질문 엔티티
//         @Entity → JPA가 이 클래스를 question 테이블과 연결한다 (ddl-auto: update 라서 테이블이 자동으로 생긴다)
//         @Getter/@Setter → Lombok이 getXxx()/setXxx() 메서드를 만들어 준다 (Setter는 004-02 이후 줄여 나간다)
@Getter
@Setter
@Entity
public class Question {
    // 004-01: 기본키(PK). IDENTITY → 번호를 DB가 1, 2, 3... 자동으로 매긴다
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 004-01: 제목. 최대 200자 → varchar(200)
    @Column(length = 200)
    private String subject;

    // 004-01: 내용. 길이 제한 없는 글 → TEXT 타입
    @Column(columnDefinition = "TEXT")
    private String content;

    // 004-01: 작성 일시. 자바의 createDate → 테이블 컬럼은 create_date (카멜 표기를 밑줄 표기로 바꿔 준다)
    //         004-02에서 BaseEntity의 @CreatedDate로 옮겨 자동으로 채우게 바꾼다
    private LocalDateTime createDate;

    // 004-01: 이 질문에 달린 답변 목록. 테이블에 컬럼이 생기지 않는다
    //         mappedBy = "question" → 관계의 주인은 Answer.question (FK는 answer 테이블에 있다)
    //         cascade = REMOVE → 질문을 지우면 답변도 함께 지운다
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}
