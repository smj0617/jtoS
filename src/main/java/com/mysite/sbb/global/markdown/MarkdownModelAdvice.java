package com.mysite.sbb.global.markdown;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

// 028-02: 새 파일. 모든 화면의 모델에 markdownService 를 넣어 준다
//         책처럼 템플릿에서 ${@markdownService.markdown(...)} 로 빈을 직접 부르면,
//         이 버전의 타임리프(3.1)는 th:utext 안의 빈 참조를 보안상 막아 오류가 난다
//         → @ControllerAdvice 의 @ModelAttribute 로 모델에 담아 ${markdownService.markdown(...)} 로 부른다
@ControllerAdvice
@RequiredArgsConstructor
public class MarkdownModelAdvice {
    private final MarkdownService markdownService;

    // 028-02: 모든 컨트롤러의 화면 응답 전에 실행되어 모델에 "markdownService" 이름으로 담긴다
    @ModelAttribute("markdownService")
    public MarkdownService markdownService() {
        return markdownService;
    }
}
