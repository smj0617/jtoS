package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

// 003-02: 테스트는 test 프로파일로 실행한다
//         → application.yaml 의 active: dev 를 덮어쓰고 application-test.yaml(메모리 DB)이 적용된다
@ActiveProfiles("test")
@SpringBootTest
class SbbApplicationTests {

    @Test
    void contextLoads() {
    }

}
