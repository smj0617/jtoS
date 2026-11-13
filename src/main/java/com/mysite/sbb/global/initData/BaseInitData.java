// 006-04: 특정 도메인이 아닌 앱 전체의 개발용 설정이라 global.initData 로 이동
package com.mysite.sbb.global.initData;

import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.domain.question.question.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

// 005-02: 새 파일. 개발 서버를 켤 때 샘플 데이터를 넣는다 (강사식 BaseInitData)
//         @Profile("dev") → dev 프로파일에서만 동작. 테스트(test 프로파일)에는 영향이 없다
//         책은 테스트 코드로 데이터를 넣었지만, 화면을 만들며 확인하려면 서버 시작 때 데이터가 있는 편이 편하다
@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final QuestionRepository questionRepository;

    // 005-02: 자기 자신의 프록시. this.work()로 부르면 @Transactional이 적용되지 않아서 프록시를 통해 부른다
    //         @Lazy → 자기 자신을 주입받을 때 생기는 순환 참조를 피한다
    @Autowired
    @Lazy
    private BaseInitData self;

    // 005-02: ApplicationRunner → 스프링 준비가 끝난 직후 한 번 실행된다
    @Bean
    public ApplicationRunner baseInitDataRunner() {
        return args -> {
            self.work();
            // 018-05: 페이징 확인용 질문을 이어서 넣는다
            self.work2();
        };
    }

    // 005-02: 이미 데이터가 있으면 넣지 않는다 (db_dev 는 파일 DB라 재시작해도 데이터가 남는다)
    @Transactional
    public void work() {
        if (questionRepository.count() > 0) return;

        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.addAnswer("스프링부트로 만든 질문답변 게시판입니다.");
        questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.addAnswer("네 자동으로 생성됩니다.");
        questionRepository.save(q2);
    }

    // 018-05: 페이징 확인용 샘플 질문 300개 (책 3-02는 테스트 코드로 300개를 넣는다, 강사식은 BaseInitData)
    //         한 페이지 10개 → 30페이지 이상이 되어 018-04 의 "앞뒤 5개만 보이기"까지 확인할 수 있다
    //         이미 넣었으면(질문이 100개 이상) 다시 넣지 않는다 → 서버를 다시 켜도 300개씩 쌓이지 않는다
    //         work() 와 나눈 이유: 이미 db_dev 에 샘플 2개가 있어 work() 는 건너뛰지만, 이 데이터는 새로 필요하다
    @Transactional
    public void work2() {
        if (questionRepository.count() >= 100) return;

        for (int i = 1; i <= 300; i++) {
            Question q = new Question();
            q.setSubject("테스트 데이터입니다:[%03d]".formatted(i));
            q.setContent("내용무");
            questionRepository.save(q);
        }
    }
}
