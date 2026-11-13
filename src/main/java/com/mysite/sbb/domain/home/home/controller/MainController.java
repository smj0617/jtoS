// 006-05: 첫 화면(/)도 하나의 기능(home)으로 보고 domain.home.home.controller 로 이동
package com.mysite.sbb.domain.home.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    // 008-01: 루트(/)로 들어오면 질문 목록으로 보낸다 (책 2-08)
    //         "redirect:주소" → 브라우저에게 그 주소로 다시 요청하라고 응답한다 (302). 주소창도 바뀐다
    //         002에서 만든 안내 문구는 더 이상 필요 없어 지웠다
    @GetMapping("/")
    public String index(){
        // 011-02: 목록 주소가 /questions 로 바뀌었다
        return "redirect:/questions";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "Hello SBB";
    }
}
