plugins {
    java
    id("org.springframework.boot") version "4.1.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.mysite"
version = "0.0.1-SNAPSHOT"
description = "jtoS"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    // 003-03: JPA 스타터 추가 (Hibernate + Spring Data JPA + JDBC/HikariCP 포함)
    //         → 004부터 자바 클래스(엔티티)를 테이블에 연결해 SQL 없이 저장·조회한다
    //         003-01에서 넣은 spring-boot-starter-jdbc 는 이 스타터 안에 들어 있으므로 지웠다
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-h2console")
    // 007-01: 타임리프 템플릿 엔진. 컨트롤러가 넘긴 데이터를 HTML 템플릿에 채워 화면을 만든다
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    // 021-01: 스프링 시큐리티. 로그인·권한·CSRF 방어를 맡는다. 넣기만 하면 모든 주소가 로그인 필요로 바뀌므로 SecurityConfig 로 규칙을 정한다
    implementation("org.springframework.boot:spring-boot-starter-security")
    // 016-05: 입력값 검증 (Bean Validation). @NotBlank, @Size 같은 어노테이션과 @Valid 를 쓸 수 있게 한다
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // 015-01: 타임리프 레이아웃 다이얼렉트 (강사식). 공통 틀(layout.html) 하나에 각 화면의 내용만 끼워 넣는다
    //         버전은 Spring Boot 의존성 관리가 정해 준다. 라이브러리만 있으면 스프링이 자동으로 등록한다
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
    runtimeOnly("com.h2database:h2")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    // 021-01: 시큐리티 테스트 도구. MockMvc 요청에 CSRF 토큰(csrf())이나 로그인 사용자(user())를 붙일 수 있다
    testImplementation("org.springframework.boot:spring-boot-starter-security-test")
    testCompileOnly("org.projectlombok:lombok")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
