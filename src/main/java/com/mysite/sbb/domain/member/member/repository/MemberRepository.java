package com.mysite.sbb.domain.member.member.repository;

import com.mysite.sbb.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 022-02: 새 파일. 회원 테이블을 다루는 리포지터리 (책 3-06의 UserRepository)
public interface MemberRepository extends JpaRepository<Member, Integer> {
    // 022-02: 아이디로 회원 찾기 → where username = ?
    //         023 로그인에서 입력한 아이디의 회원을 찾을 때 쓴다
    Optional<Member> findByUsername(String username);
}
