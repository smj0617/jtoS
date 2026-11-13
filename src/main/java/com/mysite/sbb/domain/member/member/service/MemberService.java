package com.mysite.sbb.domain.member.member.service;

import com.mysite.sbb.domain.member.member.entity.Member;
import com.mysite.sbb.domain.member.member.repository.MemberRepository;
import com.mysite.sbb.global.exception.DataNotFoundException;
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

    // 024-03: 아이디로 회원 엔티티 찾기 (책 3-08의 getUser)
    //         로그인 정보(Principal)에는 아이디 글자만 있으므로, 글쓴이로 저장하려면 이걸로 Member 를 찾아야 한다
    //         없으면 010-01 예외(404)
    public Member getMember(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("%s 회원이 없습니다.".formatted(username)));
    }
}
