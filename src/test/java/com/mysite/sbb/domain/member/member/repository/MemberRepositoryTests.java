package com.mysite.sbb.domain.member.member.repository;

import com.mysite.sbb.domain.member.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// 022-02: 새 파일. 회원 리포지터리 테스트
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    private Member newMember(String username, String email) {
        Member m = new Member();
        m.setUsername(username);
        m.setPassword("1234");
        m.setEmail(email);
        return m;
    }

    @Test
    @DisplayName("저장 후 findByUsername 으로 찾을 수 있다")
    void findByUsername() {
        memberRepository.save(newMember("user1", "user1@test.com"));

        assertThat(memberRepository.findByUsername("user1")).get()
                .extracting(Member::getEmail).isEqualTo("user1@test.com");
        assertThat(memberRepository.findByUsername("nobody")).isEmpty();
    }

    @Test
    @DisplayName("같은 아이디로 두 번 저장하면 DB 유니크 제약 때문에 예외가 난다")
    void duplicateUsername() {
        memberRepository.saveAndFlush(newMember("user1", "a@test.com"));

        assertThatThrownBy(() -> memberRepository.saveAndFlush(newMember("user1", "b@test.com")))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
