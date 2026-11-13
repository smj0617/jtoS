package com.mysite.sbb.domain.member.member.service;

import com.mysite.sbb.domain.member.member.entity.Member;
import com.mysite.sbb.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// 023-01: 새 파일. 로그인할 때 스프링 시큐리티가 "이 아이디의 회원 정보"를 물어보는 곳 (책 3-07의 UserSecurityService)
//         UserDetailsService 를 구현해 빈으로 등록하면, 시큐리티가 로그인 처리(023-03) 때 자동으로 이 클래스를 쓴다
@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {
    private final MemberRepository memberRepository;

    // 023-01: 아이디로 회원을 찾아 시큐리티용 사용자 객체(UserDetails)로 바꿔 돌려준다
    //         비밀번호 비교는 여기서 하지 않는다. 돌려준 암호화 비밀번호와 입력값을 시큐리티가 PasswordEncoder(022-03)로 비교한다
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 023-01: 권한. 모든 회원은 ROLE_MEMBER, 아이디가 admin 이면 ROLE_ADMIN 도 준다 (책과 같은 규칙)
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return new User(member.getUsername(), member.getPassword(), authorities);
    }
}
