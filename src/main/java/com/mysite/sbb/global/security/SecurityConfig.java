package com.mysite.sbb.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 021-01: 새 파일. 스프링 시큐리티 설정 (책 3-05)
//         시큐리티를 넣으면 기본값은 "모든 주소 로그인 필요 + 자동 생성 로그인 화면" 이다
//         아직 회원가입·로그인(022, 023)이 없으므로 우선 모든 요청을 허용한다 (강사식: 처음엔 전부 열고 필요한 곳만 막아 나간다)
//         여러 도메인에 걸친 설정이라 global.security 에 둔다
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // 021-01: SecurityFilterChain → 모든 요청이 컨트롤러에 닿기 전에 거치는 보안 필터들의 규칙
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 021-01: 어떤 주소든(/**) 로그인 없이 허용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll())
                // 021-02: H2 콘솔은 우리 폼이 아니라 CSRF 토큰을 보내지 못한다 → /h2-console/** 만 CSRF 검사에서 뺀다
                //         (그대로 두면 콘솔 로그인 POST 가 403 으로 막힌다)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**"))
                // 021-02: H2 콘솔 화면은 프레임(frameset)으로 나뉘어 있다
                //         시큐리티 기본값 X-Frame-Options: DENY 는 모든 프레임을 막아 콘솔이 빈 화면이 된다
                //         → 같은 사이트 안의 프레임만 허용(SAMEORIGIN). 다른 사이트가 우리 화면을 프레임에 넣는 공격(클릭재킹)은 계속 막힌다
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()))
                // 023-03: 폼 로그인 (책 3-07)
                //         loginPage → 시큐리티 자동 로그인 화면 대신 023-02 화면을 쓴다. 같은 주소로 오는 POST 를 시큐리티가 로그인 처리한다
                //         처리: MemberSecurityService(023-01)로 회원을 찾고 PasswordEncoder(022-03)로 비밀번호 비교
                //         성공 → defaultSuccessUrl("/") 로 이동, 실패 → /members/login?error 로 돌려보낸다(기본값)
                .formLogin(form -> form
                        .loginPage("/members/login")
                        .defaultSuccessUrl("/"));
        return http.build();
    }

    // 022-03: 비밀번호 암호화 도구를 빈으로 등록 (책 3-06)
    //         BCrypt → 같은 비밀번호도 매번 다른 값(솔트 포함)으로 바뀌고, 되돌릴 수 없는 해시라 DB가 털려도 원래 비밀번호를 알 수 없다
    //         가입(022-04)에서 암호화하고, 로그인(023)에서 입력값과 저장값을 matches 로 비교한다
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
