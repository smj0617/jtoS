package com.mysite.sbb.domain.member.member.controller;

import com.mysite.sbb.domain.member.member.dto.MemberJoinForm;
import com.mysite.sbb.domain.member.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    // 022-07: 가입 처리 POST /members (책 3-06)
    //         ① 022-05 검증 규칙 실패 → 폼 다시 표시
    //         ② 두 비밀번호가 다르면 → password2 칸 오류(rejectValue)로 폼 다시 표시
    //         ③ 이미 있는 아이디·이메일(022-01 유니크 제약 위반) → 글로벌 오류(reject)로 폼 다시 표시
    //         성공하면 첫 화면으로 리다이렉트 (로그인은 023)
    @PostMapping
    public String join(@Valid MemberJoinForm memberJoinForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member_join";
        }
        if (!memberJoinForm.password1().equals(memberJoinForm.password2())) {
            // 022-07: rejectValue(필드, 오류코드, 메시지) → 그 칸 아래에 메시지가 나온다
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
            return "member_join";
        }
        try {
            memberService.join(memberJoinForm.username(), memberJoinForm.email(), memberJoinForm.password1());
        } catch (DataIntegrityViolationException e) {
            // 022-07: reject(오류코드, 메시지) → 특정 칸이 아닌 글로벌 오류. 022-06 의 폼 위 빨간 상자에 나온다
            bindingResult.reject("joinFailed", "이미 등록된 아이디 또는 이메일입니다.");
            return "member_join";
        }
        return "redirect:/";
    }
}
