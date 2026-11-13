package com.mysite.sbb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// 004-02: JPA Auditing 켜기 → BaseEntity의 @CreatedDate/@LastModifiedDate 가 자동으로 채워진다
@EnableJpaAuditing
@SpringBootApplication
public class SbbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbbApplication.class, args);
    }

}
