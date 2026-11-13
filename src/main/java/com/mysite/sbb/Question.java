package com.mysite.sbb;

// 006-01: BaseEntity 가 global.jpa.entity 로 옮겨져 import 추가
import com.mysite.sbb.global.jpa.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// 004-01: 새 파일. 책 2-04 그대로의 질문 엔티티
//         @Entity → JPA가 이 클래스를 question 테이블과 연결한다 (ddl-auto: update 라서 테이블이 자동으로 생긴다)
//         @Getter/@Setter → Lombok이 getXxx()/setXxx() 메서드를 만들어 준다 (Setter는 004-02 이후 줄여 나간다)
// 004-02: BaseEntity 상속 → id, createDate, modifyDate 를 물려받는다 (여기 있던 id, createDate 필드는 삭제)
@Getter
@Setter
@Entity
public class Question extends BaseEntity {
    // 004-01: 제목. 최대 200자 → varchar(200)
    @Column(length = 200)
    private String subject;

    // 004-01: 내용. 길이 제한 없는 글 → TEXT 타입
    @Column(columnDefinition = "TEXT")
    private String content;

    // 004-01: 이 질문에 달린 답변 목록. 테이블에 컬럼이 생기지 않는다
    //         mappedBy = "question" → 관계의 주인은 Answer.question (FK는 answer 테이블에 있다)
    //         cascade = REMOVE → 질문을 지우면 답변도 함께 지운다
    // 004-03: cascade에 PERSIST 추가 → 질문을 저장할 때 answerList에 넣어 둔 새 답변도 함께 저장된다
    //         orphanRemoval = true → answerList에서 빠진 답변(고아)은 DB에서도 지운다
    //         new ArrayList<>() → 처음부터 빈 목록이라 null 검사 없이 바로 add 할 수 있다
    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Answer> answerList = new ArrayList<>();

    // 004-03: 답변 추가는 이 메서드로만 한다 → 양쪽(question.answerList, answer.question)을 한 번에 맞춰 준다
    //         한쪽만 설정하면 저장은 되어도 메모리의 객체 상태가 어긋난다
    public Answer addAnswer(String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(this);
        answerList.add(answer);
        return answer;
    }
}
