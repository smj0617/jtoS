// 006-05: 첫 화면(/)도 하나의 기능(home)으로 보고 domain.home.home.controller 로 이동
package com.mysite.sbb.domain.home.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class MainController {
    @GetMapping("/")
    @ResponseBody
    public String index(){
        return "안녕하세요, SBB입니다.";
    }
    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "Hello SBB";
    }
}
