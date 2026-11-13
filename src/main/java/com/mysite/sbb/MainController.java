package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index(){
        return "안녕하세요, SBB입니다.";
    }
    @GetMapping("/hello")
    public String hello(){
        return "Hello SBB";
    }
}
