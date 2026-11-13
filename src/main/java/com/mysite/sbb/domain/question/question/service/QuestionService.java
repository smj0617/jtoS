package com.mysite.sbb.domain.question.question.service;

import com.mysite.sbb.domain.member.member.entity.Member;
import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.domain.question.question.repository.QuestionRepository;
import com.mysite.sbb.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    // 029-02: 검색어 kw 를 받아 029-01 search 쿼리로 찾는다. kw 가 빈 문자열이면 like '%%' 라 전체가 나온다
    // 029-04: 검색 대상 kwType 도 받는다 (all, subject, content, author)
    public Page<Question> getList(int page, String kwType, String kw) {
        // 018-02: 최신 글이 먼저 오도록 정렬 조건 추가 (책 3-02)
        //         Sort.by(Sort.Order.desc("createDate")) → 작성일시 내림차순. "createDate" 는 엔티티의 필드 이름이다
        //         정렬은 DB 쿼리의 order by 로 실행된다 (페이지를 자르기 전에 정렬해야 1페이지가 최신 10개가 된다)
        // 018-06: 작성일시가 같으면 id 가 큰(나중에 저장된) 질문이 먼저 → 같은 시각에 저장된 질문도 순서가 항상 같다
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("createDate"), Sort.Order.desc("id")));
        return questionRepository.search(kwType, kw, pageable);
    }

    // 016-01: 질문 등록. 생성일시·수정일시는 BaseEntity 의 Auditing 이 채운다 (책은 setCreateDate 를 직접 호출)
    //         @Transactional → 저장 중 예외가 나면 되돌린다
    @Transactional
    // 024-04: 글쓴이(author)도 받아서 함께 저장한다
    public Question create(String subject, String content, Member author) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setAuthor(author);
        return questionRepository.save(question);
    }

    // 025-02: 질문 수정 (책 3-09)
    //         엔티티 값을 바꾸기만 하고 save 를 부르지 않는다 → 트랜잭션이 끝날 때 JPA 가 바뀐 필드를 찾아 update 를 보낸다(변경 감지)
    //         수정일시(modifyDate)는 BaseEntity 의 @LastModifiedDate 가 자동으로 갱신한다 (책은 setModifyDate 직접 호출)
    @Transactional
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
    }

    // 025-06: 질문 삭제 (책 3-09). 004-03 의 cascade REMOVE 로 달린 답변도 함께 지워진다
    @Transactional
    public void delete(Question question) {
        questionRepository.delete(question);
    }

    // 026-02: 질문 추천 (책 3-10). 추천자 Set 에 회원을 넣는다. 이미 추천했으면 Set 이라 그대로(중복 추천 안 됨)
    //         변경 감지로 question_voter 테이블에 한 줄이 추가된다
    @Transactional
    public void vote(Question question, Member member) {
        question.getVoter().add(member);
    }

    // 010-02: id로 질문 하나 조회. findById 는 Optional 을 돌려주므로, 비어 있으면 DataNotFoundException(404)을 던진다
    public Question getQuestion(Integer id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("%d번 질문이 없습니다.".formatted(id)));
    }
}
