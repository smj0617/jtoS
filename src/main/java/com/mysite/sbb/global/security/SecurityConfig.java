package com.mysite.sbb.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
                        .frameOptions(frame -> frame.sameOrigin()));
        return http.build();
    }
}
