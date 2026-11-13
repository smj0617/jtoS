package com.mysite.sbb.domain.member.member.controller;

import com.mysite.sbb.domain.member.member.dto.MemberJoinForm;
import com.mysite.sbb.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 022-06: 새 파일. 회원 화면 컨트롤러 (책 3-06의 UserController)
//         REST 규칙: 자원은 /members, 가입 입력 화면은 GET /members/new, 가입 처리는 POST /members (책은 /user/signup)
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    // 022-06: 가입 화면. 빈 MemberJoinForm 을 받아 모델에 담는다 → 템플릿의 th:object 가 쓴다 (016-06 과 같은 방식)
    @GetMapping("/new")
    public String showJoin(MemberJoinForm memberJoinForm) {
        return "member_join";
    }
}
