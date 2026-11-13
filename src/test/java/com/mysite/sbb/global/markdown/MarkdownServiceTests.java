package com.mysite.sbb.global.markdown;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// 028-01: 새 파일. 마크다운 변환 확인 (스프링 없이 객체를 직접 만들어 빠르게 검사)
class MarkdownServiceTests {
    private final MarkdownService markdownService = new MarkdownService();

    @Test
    @DisplayName("굵게, 목록, 코드가 HTML 태그로 바뀐다")
    void markdownToHtml() {
        String html = markdownService.markdown("**굵게**\n\n- 하나\n- 둘\n\n`code`");

        assertThat(html).contains("<strong>굵게</strong>");
        assertThat(html).contains("<li>하나</li>");
        assertThat(html).contains("<code>code</code>");
    }

    @Test
    @DisplayName("직접 쓴 <script> 태그는 실행되지 않게 글자로 바뀐다")
    void escapeHtml() {
        String html = markdownService.markdown("<script>alert('x')</script>");

        assertThat(html).doesNotContain("<script>");
        assertThat(html).contains("&lt;script&gt;");
    }
}
