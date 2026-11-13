package com.mysite.sbb.domain.question.answer.service;

import com.mysite.sbb.domain.member.member.entity.Member;
import com.mysite.sbb.domain.question.answer.entity.Answer;
import com.mysite.sbb.domain.question.answer.repository.AnswerRepository;
import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 012-01: 새 파일. 답변 처리를 맡는 서비스 (책 2-12)
@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    // 012-01: 답변 등록
    //         강사식: question.addAnswer() 로 양쪽 관계(question.answerList, answer.question)를 한 번에 맞춘다
    //         책은 new Answer() 후 setQuestion, setCreateDate 를 직접 했다. 생성일시는 004-02 Auditing 이 채운다
    //         @Transactional → 이 메서드 안의 DB 작업을 하나로 묶는다. 중간에 예외가 나면 모두 되돌린다
    @Transactional
    // 024-05: 글쓴이(author)도 받아서 답변에 넣는다 (024-04 질문과 같은 방식)
    public Answer create(Question question, String content, Member author) {
        Answer answer = question.addAnswer(content);
        answer.setAuthor(author);
        return answerRepository.save(answer);
    }

    // 025-08: 답변 하나 조회. 없으면 404 (010-02 getQuestion 과 같은 방식)
    public Answer getAnswer(Integer id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("%d번 답변이 없습니다.".formatted(id)));
    }

    // 025-08: 답변 수정. 025-02 처럼 값만 바꾸면 변경 감지로 update, 수정일시는 Auditing
    @Transactional
    public void modify(Answer answer, String content) {
        answer.setContent(content);
    }

    // 025-08: 답변 삭제. 질문의 answerList 에서 빼면 orphanRemoval(004-03)로 DB에서도 지워진다
    //         answerRepository.delete 만 하면 질문 쪽 목록에는 지운 답변이 그대로 남아 같은 화면에서 어긋날 수 있다
    @Transactional
    public void delete(Answer answer) {
        answer.getQuestion().getAnswerList().remove(answer);
    }

    // 026-04: 답변 추천. 026-02 질문 추천과 같다
    @Transactional
    public void vote(Answer answer, Member member) {
        answer.getVoter().add(member);
    }
}
