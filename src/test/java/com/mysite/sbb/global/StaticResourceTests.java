package com.mysite.sbb.global;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 013-01: 새 파일. static 폴더의 파일이 주소로 제공되는지 확인
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class StaticResourceTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("/resource/global.css 가 제공된다")
    void globalCss() throws Exception {
        mvc.perform(get("/resource/global.css"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("textarea")))
                // 014-02: 글꼴 지정이 들어 있는지 확인
                .andExpect(content().string(containsString("Pretendard")));
    }
}
