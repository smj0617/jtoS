package com.mysite.sbb.global.markdown;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

// 028-01: 새 파일. 마크다운 글을 HTML 로 바꾸는 서비스 (책 3-12의 CommonUtil, 강사식으로 MarkdownService 로 분리)
//         질문·답변 등 여러 도메인이 쓰므로 global.markdown 에 둔다
//         @Service 빈이라 템플릿에서 ${@markdownService.markdown(...)} 로 바로 부를 수 있다 (028-02)
// 028-02: 실제로는 타임리프 3.1 이 th:utext 안의 @빈 참조를 막아서, MarkdownModelAdvice 가 모델에 넣어 주는 방식으로 쓴다
@Service
public class MarkdownService {
    // 028-01: 파서(글 → 구조), 렌더러(구조 → HTML)는 상태가 없어 한 번 만들어 계속 쓴다
    private final Parser parser = Parser.builder().build();

    // 028-01: escapeHtml(true) → 사용자가 직접 쓴 <script> 같은 HTML 태그는 글자 그대로 보이게 바꾼다 (XSS 방지)
    //         책은 이 설정이 없어서, 내용에 <script> 를 쓰면 다른 사람 브라우저에서 실행될 수 있다
    //         sanitizeUrls(true) → [링크](javascript:...) 처럼 위험한 주소도 막는다
    private final HtmlRenderer renderer = HtmlRenderer.builder()
            .escapeHtml(true)
            .sanitizeUrls(true)
            .build();

    // 028-01: 마크다운 글자를 HTML 글자로
    public String markdown(String markdown) {
        if (markdown == null) return "";
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }
}
