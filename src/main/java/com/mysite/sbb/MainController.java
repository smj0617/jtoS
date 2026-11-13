package com.mysite.sbb;

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
