package com.mysite.sbb.domain.question.question.service;

import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.domain.question.question.repository.QuestionRepository;
import com.mysite.sbb.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 009-01: 새 파일. 질문 관련 업무 처리를 맡는 서비스 (책 2-09)
//         컨트롤러는 요청·응답만, 서비스는 실제 처리(조회·저장 규칙)만, 리포지터리는 DB 접근만 맡도록 역할을 나눈다
//         @Service → 스프링이 빈으로 등록해 컨트롤러에 주입할 수 있게 한다
//         @RequiredArgsConstructor + final 필드 → 생성자 주입 (강사식. @Autowired 필드 주입보다 테스트·불변성에 유리)
@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    // 009-01: 질문 목록 조회. 지금은 findAll() 그대로지만, 018 페이징·029 검색 같은 규칙이 이 메서드 안에 쌓인다
    // 018-01: 전체 목록 → 한 페이지(10개)만 조회 (책 3-02)
    //         page 는 0부터 시작하는 페이지 번호. PageRequest.of(page, 10) → "page 번째 페이지, 한 페이지에 10개"
    //         findAll(Pageable) 은 JpaRepository 에 이미 있다. limit/offset 쿼리와 전체 개수(count) 쿼리를 함께 실행한다
    //         Page 에는 이 페이지의 질문 목록 + 전체 개수·전체 페이지 수·이전/다음 페이지 여부가 들어 있다
    public Page<Question> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return questionRepository.findAll(pageable);
    }

    // 016-01: 질문 등록. 생성일시·수정일시는 BaseEntity 의 Auditing 이 채운다 (책은 setCreateDate 를 직접 호출)
    //         @Transactional → 저장 중 예외가 나면 되돌린다
    @Transactional
    public Question create(String subject, String content) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        return questionRepository.save(question);
    }

    // 010-02: id로 질문 하나 조회. findById 는 Optional 을 돌려주므로, 비어 있으면 DataNotFoundException(404)을 던진다
    public Question getQuestion(Integer id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("%d번 질문이 없습니다.".formatted(id)));
    }
}
