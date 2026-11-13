package com.mysite.sbb.domain.question.question.service;

import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.domain.question.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// 009-01: 새 파일. 질문 관련 업무 처리를 맡는 서비스 (책 2-09)
//         컨트롤러는 요청·응답만, 서비스는 실제 처리(조회·저장 규칙)만, 리포지터리는 DB 접근만 맡도록 역할을 나눈다
//         @Service → 스프링이 빈으로 등록해 컨트롤러에 주입할 수 있게 한다
//         @RequiredArgsConstructor + final 필드 → 생성자 주입 (강사식. @Autowired 필드 주입보다 테스트·불변성에 유리)
@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    // 009-01: 질문 목록 조회. 지금은 findAll() 그대로지만, 018 페이징·029 검색 같은 규칙이 이 메서드 안에 쌓인다
    public List<Question> getList() {
        return questionRepository.findAll();
    }
}
