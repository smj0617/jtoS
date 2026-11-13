package com.mysite.sbb.domain.member.member.service;

import com.mysite.sbb.domain.member.member.entity.Member;
import com.mysite.sbb.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 022-04: 새 파일. 회원 처리 서비스 (책 3-06의 UserService)
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    // 022-04: 022-03 에서 빈으로 등록한 BCrypt 암호화 도구를 주입받는다
    private final PasswordEncoder passwordEncoder;

    // 022-04: 회원가입. 비밀번호는 평문이 아니라 passwordEncoder.encode() 결과를 저장한다
    //         강사식 이름 join (책은 create)
    @Transactional
    public Member join(String username, String email, String password) {
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));
        return memberRepository.save(member);
    }
}
