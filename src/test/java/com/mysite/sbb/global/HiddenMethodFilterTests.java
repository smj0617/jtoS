package com.mysite.sbb.global;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import static org.assertj.core.api.Assertions.assertThat;

// 025-01: 새 파일. _method 필터가 켜졌는지 확인
@ActiveProfiles("test")
@SpringBootTest
class HiddenMethodFilterTests {
    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("spring.mvc.hiddenmethod.filter.enabled=true 이면 HiddenHttpMethodFilter 빈이 등록된다")
    void filterRegistered() {
        assertThat(context.getBeansOfType(HiddenHttpMethodFilter.class)).isNotEmpty();
    }
}
