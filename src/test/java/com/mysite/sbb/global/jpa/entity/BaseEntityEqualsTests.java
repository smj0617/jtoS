package com.mysite.sbb.global.jpa.entity;

import com.mysite.sbb.domain.member.member.entity.Member;
import com.mysite.sbb.domain.member.member.repository.MemberRepository;
import com.mysite.sbb.domain.member.member.service.MemberService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

// 026-01: 새 파일. BaseEntity 의 equals/hashCode 확인
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class BaseEntityEqualsTests {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("따로 읽어 온 같은 id 의 회원은 같다고 보고, Set 에 두 번 넣어도 하나만 남는다")
    void sameIdIsEqual() {
        Member saved = memberService.join("user1", "user1@test.com", "1234");
        em.flush();
        em.clear(); // 1차 캐시를 비워 다음 조회가 새 객체를 만들게 한다

        Member loaded = memberRepository.findById(saved.getId()).get();
        assertThat(loaded).isNotSameAs(saved);   // 메모리상 다른 객체
        assertThat(loaded).isEqualTo(saved);     // 하지만 같은 회원

        Set<Member> set = new HashSet<>();
        set.add(saved);
        set.add(loaded);
        assertThat(set).hasSize(1);
    }

    @Test
    @DisplayName("저장 전(id 없음) 엔티티끼리는 다르다")
    void unsavedAreDifferent() {
        assertThat(new Member()).isNotEqualTo(new Member());
    }
}
