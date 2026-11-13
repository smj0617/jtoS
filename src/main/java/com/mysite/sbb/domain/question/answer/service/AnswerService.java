package com.mysite.sbb.domain.question.answer.service;

import com.mysite.sbb.domain.member.member.entity.Member;
import com.mysite.sbb.domain.question.answer.entity.Answer;
import com.mysite.sbb.domain.question.answer.repository.AnswerRepository;
import com.mysite.sbb.domain.question.question.entity.Question;
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
}
