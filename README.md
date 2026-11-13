# jtoS - 점프 투 스프링부트 복습

## 목표
- **1단계**: 『점프 투 스프링부트』(SBB)의 기능을 책 순서대로 구현하면서, 장희성 강사의 수업 내용으로 개선할 수 있는 부분을 적용한다.
- **2단계 (선택)**: 1단계를 마친 뒤 시간이 되면 책의 [3-14 SBB 추가 기능 구현하기](https://wikidocs.net/162814) 도전과제를 하나씩 구현한다.

## 진행 규칙
- AI는 나의 이해 속도보다 앞서 나가지 않는다.
- 단계마다 할 일과 이유를 먼저 설명받고, 내가 승인한 뒤에 실행한다.
- 한 번에 작은 작업 하나만 진행하고, 결과는 직접 실행해서 눈으로 확인한다.
- 커밋은 작게, 자주 한다.
  - 제목: `기능번호-세부번호 기능명, 세부작업` (예: `004-02 엔티티, BaseEntity 도입`)
  - 본문: 작업 내용 / 작업 이유 / 검증
- 모든 커밋마다 아래 `## 작업내역`에 기록을 누적한다.

## 기술 스택
- Java 25
- Spring Boot 4.1.2
- Gradle (Kotlin DSL)
- Spring Data JPA, H2
- Thymeleaf
- Spring Security

## 참고 자료
- 점프 투 스프링부트: [교재](https://wikidocs.net/book/7601) / [GitHub](https://github.com/pahkey/sbb3)
- 장희성 강사 수업 (2026-11-13 수업까지 진행한 범위)
  - 14158 스프링부트, JPA 기초: 1~56강
  - 14163 스프링부트, JSB: 1~39강 / [강의](https://www.slog.gg/p/14163) / [GitHub](https://github.com/jhs512/p-14163-2)
  - 14164 스프링부트, 컨트롤러, 빈, AOP: 1~37강
  - 14168 스프링부트, SpringWeb, 타임리프, 벨리데이션: 1~46강 / [강의](https://www.slog.gg/p/14168) / [GitHub](https://github.com/jhs512/p-14168-2)

## 작업 계획

### 2장 기본기 다지기
| 번호 | 책 | 적용할 개선점 |
|---|---|---|
| 001 | 2-01 프로젝트 구조 | Boot 4.1.2 / Java 25 / `build.gradle.kts` |
| 002 | 2-02 컨트롤러 | 404가 나오는 과정 확인 |
| 003 | 2-03 JPA | H2 파일 모드(dev) + 메모리 모드(test) 프로파일 분리, SQL 로그 상세 출력 |
| 004 | 2-04 엔티티 | `BaseEntity`, `@CreatedDate`/`@LastModifiedDate`, `@ManyToOne(fetch = LAZY)`, cascade/orphanRemoval |
| 005 | 2-05 리포지터리 | `BaseInitData` 샘플 데이터, 테스트 `@Transactional` 롤백 |
| 006 | 2-06 도메인별 분류 | `domain.question.question.{controller,service,repository,entity}` 구조 |
| 007 | 2-07 질문 목록 | 타임리프 캐시는 dev에서만 끔 |
| 008 | 2-08 루트 URL | - |
| 009 | 2-09 서비스 | `@RequiredArgsConstructor` + `final` 생성자 주입 |
| 010 | 2-10 상세 | 친절한 메시지의 커스텀 예외 + 404 |
| 011 | 2-11 URL 프리픽스 | 복수형 리소스 URL `/questions` |
| 012 | 2-12 답변 | `question.addAnswer()` + cascade, `POST /questions/{id}/answers` |
| 013 | 2-13 웹 디자인 | 정적 파일은 `static/resource` 폴더 |
| 014 | 2-14 부트스트랩 | cdnjs CDN, Pretendard 폰트 |
| 015 | 2-15 표준 HTML | Layout Dialect `layout.html`, `layout:title-pattern`, `global.js` |
| 016 | 2-16 질문 등록 | `record` 폼, `BindingResult`, `th:object`/`th:field`, 첫 오류 필드 focus, 클라이언트·서버 양쪽 검증 |

### 3장 SBB 서비스 개발
| 번호 | 책 | 적용할 개선점 |
|---|---|---|
| 017 | 3-01 내비게이션 바 | 프래그먼트로 분리 |
| 018 | 3-02 페이징 | - |
| 019 | 3-03 게시물 번호 | - |
| 020 | 3-04 답변 개수 | `@OneToMany` 컬렉션 활용 |
| 021 | 3-05 스프링 시큐리티 | 처음엔 모든 요청 허용, `th:action`으로 CSRF 처리 |
| 022 | 3-06 회원가입 | `record` 폼 + 검증, `BaseEntity` 상속 |
| 023 | 3-07 로그인/로그아웃 | - |
| 024 | 3-08 글쓴이 | `@ManyToOne(fetch = LAZY) author` |
| 025 | 3-09 수정·삭제 | `_method` → `@PutMapping`/`@DeleteMapping`, `<a method="DELETE">` |
| 026 | 3-10 추천 | `equals`/`hashCode` |
| 027 | 3-11 앵커 | - |
| 028 | 3-12 마크다운 | `MarkdownService`로 분리 |
| 029 | 3-13 검색 | 검색 옵션 `select` 기본값 JS |

### 2단계 도전과제 (선택)
- 1단계(001~029)를 마친 뒤 시간이 되면 030번부터 하나씩 진행

## 작업내역

### 000-01 프로젝트 준비, README와 진행 계획 작성
- 작업: git 저장소 생성, README에 목표·진행 규칙·기술 스택·참고 자료·작업 계획 작성
- 이유: 코드를 쓰기 전에 무엇을 어떤 순서로, 어떤 규칙으로 할지 정해 두기 위해
- 결과: GitHub 공개 저장소 `smj0617/jtoS`에 push

### 001-01 프로젝트 생성, Spring Boot 4.1.2 프로젝트 생성
- 작업: IntelliJ로 Spring Boot 프로젝트 생성 (Java 25, Gradle Kotlin DSL, YAML 설정, 의존성 Spring Web·Lombok·DevTools)
- 이유: 강사(14163 2강)와 같은 최신 환경으로 시작하기 위해. JPA·H2·Thymeleaf는 책 순서대로 003·007에서 추가한다
- 결과: `localhost:8080` 접속 시 Whitelabel Error Page(404). 컨트롤러가 없으니 정상
- 메모: IntelliJ는 Artifact가 아니라 Name(`jtoS`)으로 메인 클래스 이름을 지어서 `JtoSApplication`이 됨 → 001-02에서 변경

### 001-02 프로젝트 생성, 메인 클래스 이름을 SbbApplication으로 변경
- 작업: `JtoSApplication` → `SbbApplication`, `JtoSApplicationTests` → `SbbApplicationTests` (IntelliJ Rename, Shift+F6), `spring.application.name`을 `sbb`로 변경
- 이유: 책과 강의(14163 2강) 모두 `SbbApplication`을 쓰므로, 이름을 맞춰 두면 코드를 비교할 때 헷갈리지 않는다
- 결과: `SbbApplicationTests` 통과, 실행 로그에 `Started SbbApplication`, `[sbb]` 표시
- 메모: IntelliJ Rename의 "텍스트 속 이름도 바꾸기" 옵션 때문에 README의 001-01 메모까지 바뀌어서 원래대로 되돌림

### 002-01 컨트롤러, MainController와 액션 메서드 2개 추가 (@ResponseBody 없이)
- 작업: `@Controller` `MainController`에 `@GetMapping("/")`, `@GetMapping("/hello")` 액션 메서드 추가
- 이유: `@ResponseBody`가 없으면 리턴한 문자열이 어떻게 처리되는지 직접 보기 위해 (14163 3강)
- 결과: `/`, `/hello`, `/not-exist` 모두 404. 하지만 원인이 다르다
  - `/not-exist`: 매핑된 액션 메서드가 없음 → 정적 파일에서 찾다가 실패 (`No static resource not-exist`)
  - `/hello`: 액션 메서드는 실행됨 → 리턴값 `Hello SBB`를 **화면(뷰) 이름**으로 해석 → `/Hello SBB`로 forward → 그런 파일이 없어서 404 (`No static resource Hello SBB`, 스택에 `InternalResourceView.forward`)

### 002-02 컨트롤러, 액션 메서드에 @ResponseBody 추가
- 작업: `index()`, `hello()`에 `@ResponseBody` 추가
- 이유: 액션 메서드의 리턴값을 화면 이름이 아니라 **응답 본문 그대로** 브라우저에 보내기 위해 (14163 4강)
- 결과: `/` → `안녕하세요, SBB입니다.`, `/hello` → `Hello SBB` (200). `/not-exist`는 여전히 404

### 000-02 진행 계획 수정, 2단계 도전과제를 선택 과정으로 변경
- 작업: README 목표와 작업 계획에서 2단계를 "시간이 되면 하는 선택 과정"으로 변경
- 이유: 1단계(책 + 강의 개선, 001~029)를 끝까지 제대로 마치는 데 집중하기 위해
- 결과: 1단계가 필수 범위, 2단계(030~)는 선택 범위

### 003-01 JPA, H2 데이터베이스와 H2 콘솔 추가
- 작업: 의존성 `h2`, `spring-boot-h2console`, `spring-boot-starter-jdbc` 추가. `application.yaml`에 H2 콘솔(`/h2-console`)과 파일 모드 DB(`jdbc:h2:./db_dev`) 설정
- 이유: 004에서 만들 엔티티를 저장할 DB를 준비하기 위해 (책 2-03, 14163 5강)
- 결과: 시작 로그에 `HikariPool-1 - Start completed`, `H2 console available at '/h2-console'`. 프로젝트 폴더에 `db_dev.mv.db` 생성. `/h2-console`에서 `jdbc:h2:./db_dev`로 로그인 성공
- 메모 1: Boot 4는 H2 콘솔 자동 설정이 별도 모듈이라 `spring-boot-h2console` 의존성이 필요하다 (책 Boot 3에는 없음)
- 메모 2: 처음에는 JDBC 없이 시도했더니 콘솔에서 `Database ... db_dev not found` 오류. H2는 콘솔(원격)에서 DB 파일을 새로 만들지 않는데, JDBC가 없으면 앱이 `datasource` 설정을 쓰지 않아 파일이 만들어지지 않았다. `starter-jdbc`를 추가하자 앱이 시작하면서 DB에 연결해 파일을 만들었다. 003-03에서 JPA 스타터(JDBC 포함)를 넣으면 이 줄은 지운다
- 메모 3: `db_dev.mv.db`는 커밋하지 않는다. 003-02에서 `.gitignore`에 추가

### 003-02 JPA, dev/test 프로파일 분리
- 작업: DB 설정을 `application-dev.yaml`(파일 DB `jdbc:h2:./db_dev`)과 `src/test/resources/application-test.yaml`(메모리 DB `jdbc:h2:mem:db_test`)로 나눴다. `application.yaml`에 `spring.profiles.active: dev`, 테스트 클래스에 `@ActiveProfiles("test")`. `.gitignore`에 `db_dev.mv.db`, `db_dev.trace.db` 추가
- 이유: 테스트가 개발 DB를 건드리지 않고 매번 깨끗한 DB에서 돌게 하려고. 강사 강의의 dev/test 분리 방식을 따름 (14158 JPA 기초)
- 결과: `gradlew test` 통과(1개), 로그 `The following 1 profile is active: "test"`, `url=jdbc:h2:mem:db_test`. `bootRun` 로그 `profile is active: "dev"`, `url=jdbc:h2:./db_dev`. `git status`에 `db_dev.mv.db`가 더 이상 보이지 않음
- 메모: `@ActiveProfiles("test")`는 `application.yaml`의 `active: dev`보다 우선한다. `application-test.yaml`은 `src/test/resources`에 있어 테스트 실행 때만 읽힌다
### 003-03 JPA, JPA 스타터 추가와 ddl-auto 설정
- 작업: `spring-boot-starter-data-jpa` 추가, `spring-boot-starter-jdbc` 삭제. `application-dev.yaml`에 `ddl-auto: update`, `application-test.yaml`에 `ddl-auto: create-drop`
- 이유: 004부터 엔티티 클래스를 테이블에 연결해 SQL 없이 저장·조회하려고 (책 2-03, 14158 JPA 기초). dev는 데이터를 유지해야 하고 test는 매번 빈 테이블에서 시작해야 해서 값을 나눴다
- 결과: `gradlew test` 통과, `bootRun`·테스트 모두 `Hibernate ORM core version 7.4.13.Final`, `Initialized JPA EntityManagerFactory` 로그. 엔티티가 아직 없어 테이블은 생기지 않는다
- 메모 1: JPA 스타터에 JDBC·HikariCP가 들어 있어 003-01의 `starter-jdbc`는 지웠다. DB 연결과 `db_dev.mv.db` 생성은 그대로 된다
- 메모 2: 책의 `hibernate.dialect: H2Dialect` 설정은 넣지 않았다. Hibernate 7은 DB 종류를 스스로 알아낸다
- 메모 3: 시작 로그에 `spring.jpa.open-in-view is enabled by default` 경고가 뜬다. 지금은 그대로 두고, 화면(템플릿)을 만드는 단계에서 다룬다
### 003-04 JPA, SQL 로그 보기 좋게 설정
- 작업: `application.yaml`에 `spring.output.ansi.enabled: always`, Hibernate `format_sql`·`highlight_sql`·`use_sql_comments: true`, 로그 레벨 `org.hibernate.SQL: DEBUG`, `org.hibernate.orm.jdbc.bind: TRACE`
- 이유: 004부터 JPA가 만든 SQL과 그 SQL에 들어간 값을 로그로 읽으며 공부하려고 (강사 강의 14158 JPA 기초의 SQL 로그 설정). dev와 test 모두 쓰므로 공통 파일에 넣었다
- 결과: 임시 테스트(`select 1 + ?`, 값 41)로 확인. 로그에 `/* dynamic native SQL query */` 주석, 줄바꿈된 SQL, 색이 입혀진 키워드, `binding parameter (1:INTEGER) <- [41]`가 찍혔다. 임시 테스트는 확인 후 지웠다. `gradlew test` 통과, `bootRun` 정상 시작
- 메모 1: `show_sql: true`는 `System.out`으로 찍혀 로그 형식(시간·레벨)이 빠진다. 그래서 `org.hibernate.SQL` 로거를 DEBUG로 켰다
- 메모 2: 책에 나오는 `org.hibernate.type.descriptor.sql.BasicBinder`는 Hibernate 5 시절 이름이다. Hibernate 6 이상은 `org.hibernate.orm.jdbc.bind`
### 004-01 엔티티, 책 그대로 Question·Answer 엔티티 작성
- 작업: `com.mysite.sbb`에 `Question`(id, subject, content, createDate, answerList)과 `Answer`(id, content, createDate, question) 엔티티 추가. 책 2-04 코드 그대로(`@Setter`, `@ManyToOne` 기본 EAGER, `cascade = REMOVE`)
- 이유: 강사식 개선(004-02 BaseEntity, 004-03 LAZY)을 하기 전에 책의 기본 모양을 먼저 남겨 두고, 무엇이 왜 바뀌는지 커밋으로 비교하려고
- 결과: 테스트와 `bootRun` 로그에 `create table question`(subject `varchar(200)`, content `TEXT`, `create_date timestamp(6)`), `create table answer`(`question_id`), `alter table answer add constraint FK... foreign key (question_id) references question`. H2 콘솔 `jdbc:h2:./db_dev`에 `QUESTION`, `ANSWER` 테이블이 보인다
- 메모 1: 자바 필드 `createDate`는 컬럼 `create_date`가 된다 (Spring Boot 기본 이름 규칙: 카멜 표기 → 밑줄 표기)
- 메모 2: `answerList`(`@OneToMany(mappedBy)`)는 컬럼을 만들지 않는다. 외래키는 관계의 주인인 `Answer.question` 쪽 테이블에만 생긴다
- 메모 3: 패키지는 006에서 도메인별로 옮긴다. 지금은 책 순서대로 `com.mysite.sbb`에 둔다

### 004-02 엔티티, BaseEntity 도입
- 작업: `BaseEntity`(`@MappedSuperclass`, id·`@CreatedDate createDate`·`@LastModifiedDate modifyDate`) 추가, `Question`·`Answer`가 상속. `SbbApplication`에 `@EnableJpaAuditing`
- 이유: 모든 엔티티에 반복되는 id와 날짜 필드를 한곳에 모으고, 날짜를 직접 넣지 않아도 저장·수정 때 자동으로 채워지게 하려고 (강사식 개선)
- 결과: `gradlew test` 통과. `question`, `answer` 테이블에 `modify_date timestamp(6)` 컬럼이 추가로 생겼다. 날짜가 실제로 채워지는지는 005 리포지터리 테스트에서 확인
- 메모: 책은 `question.setCreateDate(LocalDateTime.now())`로 직접 넣는다. Auditing을 쓰면 이 줄이 필요 없다


### 004-03 엔티티, LAZY 로딩과 cascade 정리
- 작업: `Answer.question`을 `@ManyToOne(fetch = LAZY)`로 변경. `Question.answerList`에 `cascade = {PERSIST, REMOVE}`, `orphanRemoval = true`, `new ArrayList<>()` 초기화. 양쪽 관계를 한 번에 맞추는 `Question.addAnswer(content)` 추가
- 이유: EAGER는 답변을 읽을 때마다 질문까지 읽어 쿼리가 늘어난다(N+1). 답변은 질문을 통해서만 추가·삭제되게 해서 관계를 한쪽에서 관리하려고 (강사식 개선)
- 결과: `gradlew test` 통과. 실제 동작(질문 저장 시 답변 함께 저장)은 005 테스트에서 확인


### 005-01 리포지터리, QuestionRepository·AnswerRepository와 테스트
- 작업: `JpaRepository`를 상속한 `QuestionRepository`(`findBySubject`, `findBySubjectAndContent`, `findBySubjectLike`), `AnswerRepository` 추가. 책 2-05의 테스트를 `QuestionRepositoryTests` 하나에 모으고 클래스에 `@Transactional`
- 이유: 엔티티를 저장·조회할 통로가 필요하다(책 2-05). 테스트는 메서드마다 롤백해 서로의 데이터에 영향을 주지 않게 했다(강사식). 책은 남은 데이터 때문에 id를 맞춰 가며 테스트를 고쳐야 했다
- 결과: 테스트 5개 통과. 저장 시 `createDate`·`modifyDate` 자동 기록(004-02), `addAnswer` 후 질문만 저장해도 답변 저장(004-03 cascade)까지 확인
- 메모: 메서드 이름 규칙(`findBy` + 필드명 + `And`/`Like`)만으로 Spring Data JPA가 쿼리를 만든다


### 005-02 리포지터리, BaseInitData로 개발용 샘플 데이터 넣기
- 작업: `BaseInitData`(`@Profile("dev")`, `@Configuration`, `ApplicationRunner` 빈) 추가. 질문이 하나도 없을 때만 질문 2개와 각 답변 1개를 저장
- 이유: 007부터 화면을 만들 때 서버를 켜자마자 볼 데이터가 있어야 한다. 책은 테스트 코드로 데이터를 넣었지만 강사식은 서버 시작 때 넣는다. test 프로파일에는 영향이 없다
- 결과: `bootRun` 로그에 insert SQL 8줄과 `binding parameter ... <- [sbb가 무엇인가요?]`, 생성·수정 일시 값이 찍혔다
- 메모 1: `work()`는 `self.work()`로 부른다. `this.work()`로 부르면 스프링 프록시를 거치지 않아 `@Transactional`이 적용되지 않는다. 자기 자신을 주입받으면 순환 참조가 되므로 `@Lazy`를 붙였다
- 메모 2: 004에서 테이블 구조가 바뀌어 기존 `db_dev.mv.db`를 지우고 새로 만들었다 (`ddl-auto: update`는 컬럼 추가는 하지만 지운 컬럼·바뀐 제약은 정리하지 않는다)


### 006-01 도메인별 분류, 공통 엔티티 BaseEntity를 global.jpa.entity로
- 작업: `BaseEntity`를 `com.mysite.sbb`에서 `com.mysite.sbb.global.jpa.entity`로 옮겼다. 아직 옮기지 않은 `Question`, `Answer`(같은 패키지라 import가 없던 파일)에 새 위치의 `BaseEntity` import를 추가했다
- 이유: 책 2-06처럼 파일을 기능별로 나누기 시작한다. 강사식 구조에서 여러 도메인이 함께 쓰는 코드는 `global` 아래에 둔다. 다른 클래스가 상속·의존하는 것부터 옮겨야 각 단계에서 컴파일이 깨지지 않는다
- 결과: `global/jpa/entity/BaseEntity.java`로 이동. 질문·답변을 저장하면 생성·수정 일시가 그대로 채워진다(`QuestionRepositoryTests`의 Auditing 테스트 포함 테스트 6개 통과)
- 메모: 같은 패키지 안의 클래스는 import 없이 쓸 수 있지만, 패키지가 달라지면 import가 필요하다. 그래서 옮긴 파일뿐 아니라 그 파일을 쓰는 쪽도 함께 고쳐야 한다

### 006-02 도메인별 분류, 질문 도메인 domain.question.question으로
- 작업: `Question` → `domain.question.question.entity`, `QuestionRepository` → `domain.question.question.repository`, 테스트 `QuestionRepositoryTests`도 같은 패키지로 옮겼다. 아직 남아 있는 `Answer`, `BaseInitData`에 새 위치의 import를 추가했다
- 이유: 강사식 3단 구조 `도메인.하위도메인.역할`. 질문 기능 파일을 한곳에 모으고, 역할(entity, repository, 이후 service·controller)로 한 단계 더 나눈다
- 결과: 질문 관련 파일 3개 이동. 질문 저장·목록·이름 조회·수정·삭제 테스트가 새 패키지에서 그대로 통과(6개)
- 메모: `domain.question.question`에서 첫 `question`은 큰 도메인(질문 게시판), 두 번째 `question`은 그 안의 하위 도메인(질문)이다. 답변은 같은 큰 도메인 아래 `domain.question.answer`가 된다

### 006-03 도메인별 분류, 답변을 질문의 하위 도메인 domain.question.answer로
- 작업: `Answer` → `domain.question.answer.entity`, `AnswerRepository` → `domain.question.answer.repository`. `Question`과 `QuestionRepositoryTests`가 쓰던 답변 import를 새 위치로 바꿨다
- 이유: 답변은 질문 없이 존재하지 않는 질문의 일부라서, 별도의 큰 도메인이 아니라 `question` 도메인의 하위 도메인으로 둔다 (강사식)
- 결과: 답변 파일 2개 이동. `addAnswer` 후 질문만 저장해도 답변이 함께 저장되는 테스트(004-03 cascade)가 그대로 통과(6개)

### 006-04 도메인별 분류, 개발용 초기 데이터 BaseInitData를 global.initData로
- 작업: `BaseInitData`를 `global.initData`로 옮겼다
- 이유: 특정 도메인의 기능이 아니라 앱 전체의 개발용 설정이라 `global`에 둔다 (강사식)
- 결과: dev 서버를 켤 때 샘플 질문 2개를 넣는 동작은 그대로다. `@Configuration`은 `SbbApplication` 아래 패키지면 어디에 있어도 자동으로 등록된다. 테스트 6개 통과

### 006-05 도메인별 분류, 메인 컨트롤러를 domain.home.home.controller로
- 작업: `MainController`를 `domain.home.home.controller`로 옮겼다. 이로써 `com.mysite.sbb` 바로 아래에는 `SbbApplication`만 남는다
- 이유: 첫 화면(`/`)도 하나의 기능(home)으로 보고 같은 3단 구조를 따른다 (강사식)
- 결과: `/`, `/hello` 응답이 그대로 나온다. 패키지 구조 정리가 끝났다. 테스트 6개 통과
- 메모: `@SpringBootApplication`은 자기 패키지(`com.mysite.sbb`)와 그 아래 모든 패키지를 스캔한다. 그래서 패키지를 깊게 나눠도 컨트롤러·설정 빈 등록에는 문제가 없다

### 007-01 질문 목록, 타임리프 의존성 추가
- 작업: `build.gradle.kts`에 `spring-boot-starter-thymeleaf` 추가
- 이유: 컨트롤러가 넘긴 데이터를 HTML 템플릿에 채워 화면을 만드는 템플릿 엔진이 필요하다 (책 2-07)
- 결과: 타임리프 자동 설정이 들어와 `src/main/resources/templates/` 아래 HTML을 화면으로 쓸 수 있게 됐다. 아직 템플릿은 없어 화면 변화는 없다. 테스트 6개 통과

### 007-02 질문 목록, dev에서만 템플릿 캐시 끄기
- 작업: `application-dev.yaml`에 `spring.thymeleaf.cache: false`
- 이유: 타임리프는 기본으로 템플릿을 캐시해서 HTML을 고쳐도 서버를 다시 켜야 반영된다. 개발 중에만 끄고 운영(기본값 true)은 그대로 둔다 (강사식, 책에는 없는 설정)
- 결과: dev 서버에서 HTML을 고치면 새로고침만으로 반영된다. test 프로파일과 운영 설정에는 영향이 없다. 테스트 6개 통과

### 007-03 질문 목록, QuestionController와 question_list.html
- 작업: `domain.question.question.controller.QuestionController`의 `GET /question/list`가 `questionRepository.findAll()` 결과를 `Model`에 `questionList`로 담고 `question_list`를 반환. `templates/question_list.html`은 `th:each`로 제목·작성일시 표를 그린다. MockMvc 테스트 `QuestionControllerTests` 추가
- 이유: 질문 목록 화면 (책 2-07). 서버와 브라우저 없이 화면 결과를 빠르게 확인하려고 MockMvc 테스트를 함께 둔다
- 결과: `GET /question/list` → 200, 뷰 이름 `question_list`, 응답 HTML에 질문 제목 "sbb가 무엇인가요?"가 들어 있다. 테스트 7개 통과
- 메모: `@ResponseBody`가 없으면 반환한 문자열은 템플릿 이름이다(002-01에서 404가 났던 이유와 같다). 이번에는 템플릿이 있어서 화면이 그려진다

### 008-01 루트 URL, / 를 질문 목록으로 리다이렉트
- 작업: `MainController.index()`가 `redirect:/question/list`를 반환. 002의 안내 문구 응답은 삭제
- 이유: 사이트 첫 화면을 질문 목록으로 (책 2-08)
- 결과: 테스트 `/ → 302, /question/list` 통과
- 메모: `redirect:`는 서버가 화면을 그리지 않고 브라우저에 "이 주소로 다시 요청하라"(302)고 응답한다. 주소창도 바뀐다


### 009-01 서비스, QuestionService 생성과 목록 조회
- 작업: `domain.question.question.service.QuestionService` 추가. `@Service`, `@RequiredArgsConstructor` + `private final QuestionRepository`로 생성자 주입. 질문 전체를 돌려주는 `getList()`(`findAll()` 호출)
- 이유: 컨트롤러(요청·응답), 서비스(업무 처리), 리포지터리(DB 접근)로 역할을 나누려고 (책 2-09). 페이징·검색 같은 조회 규칙이 생기면 컨트롤러가 아니라 서비스에 쌓인다. 필드 `@Autowired` 대신 `final` + 생성자 주입은 강사식(값이 바뀌지 않고 테스트에서 직접 넣기 쉽다)
- 결과: 서비스 빈이 만들어져 앱이 정상 시작. 아직 컨트롤러는 리포지터리를 직접 쓰고 있어 화면 결과는 그대로. 기존 테스트 8개 통과
- 메모: 이 단계는 서비스 "추가"만 했다. 컨트롤러를 서비스로 바꾸는 것은 009-02로 나눴다

### 009-02 서비스, 컨트롤러가 리포지터리 대신 서비스 사용
- 작업: `QuestionController`의 주입 대상을 `QuestionRepository` → `QuestionService`로 바꾸고, `list()`에서 `questionRepository.findAll()` 대신 `questionService.getList()` 호출
- 이유: 컨트롤러가 DB를 직접 알지 않고 처리를 서비스에 맡기게 하려고 (책 2-09)
- 결과: `GET /question/list` 응답 HTML에 질문 제목이 그대로 나온다(MockMvc 목록 테스트가 고치지 않고 통과). 사용자가 보는 화면은 바뀌지 않고 내부 구조만 바뀌었다
- 메모: 겉으로 결과가 같아야 하는 구조 변경은 기존 테스트가 그대로 통과하는지로 확인한다

### 009-03 서비스, QuestionService 테스트 추가
- 작업: `QuestionServiceTests` 추가. 질문 2개("질문1", "질문2")를 저장하고 `getList()`가 두 제목을 그 순서로 돌려주는지 검사. 클래스에 `@Transactional`(테스트마다 롤백)
- 이유: 화면(컨트롤러) 테스트와 처리(서비스) 테스트를 나눠 두면, 실패했을 때 어느 층의 문제인지 바로 알 수 있다
- 결과: `getList()`가 저장한 질문 2개를 순서대로 돌려준다. 전체 테스트 9개 통과

### 010-01 상세, 데이터가 없을 때 쓰는 DataNotFoundException 추가
- 작업: `global.exception.DataNotFoundException` 추가. `RuntimeException` 상속, 클래스에 `@ResponseStatus(HttpStatus.NOT_FOUND)`, 생성자로 메시지를 받는다
- 이유: 없는 번호의 질문을 요청하면 서버 오류(500)가 아니라 "없음"(404)으로 응답해야 한다 (책 2-10). 책은 고정 문구 `"entity not found"`, 강사식은 무엇이 없는지 메시지를 직접 받는다. 여러 도메인이 같이 쓰므로 `global`에 둔다
- 결과: 예외 클래스만 추가. 실제로 던지는 곳은 010-02, 404가 나는 것은 010-03에서 확인. 테스트 9개 통과
- 메모: `RuntimeException`(언체크 예외)이라 메서드마다 `throws`를 적지 않아도 된다

### 010-02 상세, QuestionService.getQuestion 단건 조회
- 작업: `QuestionService.getQuestion(id)` 추가. `findById(id).orElseThrow(() -> new DataNotFoundException("%d번 질문이 없습니다."))`. 서비스 테스트에 성공·실패 경우 추가
- 이유: 상세 화면에 보여 줄 질문 하나를 찾기 위해 (책 2-10). `findById`는 `Optional`을 돌려주므로, 비어 있을 때 `get()`으로 꺼내다 터지지 않게 `orElseThrow`로 명확히 처리
- 결과: 있는 id는 그 질문을 돌려주고, 없는 id 999999는 `DataNotFoundException`과 메시지 `999999번 질문이 없습니다.`가 난다. 테스트 10개 통과

### 010-03 상세, 질문 상세 화면
- 작업: `QuestionController.detail()` 추가. `GET /question/detail/{id}`, `@PathVariable Integer id`로 주소의 번호를 받아 `getQuestion(id)` 결과를 모델 `question`에 담고 `question_detail.html`(제목 `h1`, 내용 `div`)을 그린다
- 이유: 질문 하나의 내용을 보는 화면 (책 2-10)
- 결과: `GET /question/detail/{id}` → 200, 응답에 질문 내용 "sbb에 대해서 알고 싶습니다."가 나온다. `GET /question/detail/999999` → 404 (010-01 `@ResponseStatus`가 동작). 테스트 11개 통과

### 010-04 상세, 목록의 제목을 상세 화면 링크로
- 작업: `question_list.html`의 제목 칸을 `<a th:href="@{|/question/detail/${question.id}|}">`로 감쌈
- 이유: 목록에서 제목을 눌러 상세로 이동하게 (책 2-10)
- 결과: 목록 응답 HTML에 `href="/question/detail/{질문 id}"` 링크가 생겼다. 목록 → 상세 이동이 가능해졌다
- 메모: `@{}`는 앱 기준 주소를 만들고, `|...|` 안에서는 글자와 `${}` 값을 이어 붙일 수 있다

### 011-01 URL 프리픽스, 컨트롤러에 공통 주소 /question 적용
- 작업: `QuestionController` 클래스에 `@RequestMapping("/question")`을 붙이고 메서드 주소를 `/list`, `/detail/{id}`로 줄였다
- 이유: 같은 컨트롤러의 주소 앞부분을 한곳에서 관리하려고 (책 2-11)
- 결과: 실제 주소는 `/question/list`, `/question/detail/{id}` 그대로. 기존 목록·상세·404 테스트가 고치지 않고 통과(11개)

### 011-02 URL 프리픽스, REST 주소 /questions, /questions/{id}로 변경
- 작업: 공통 주소를 `@RequestMapping("/questions")`로, 목록은 `@GetMapping`(= `GET /questions`), 상세는 `@GetMapping("/{id}")`(= `GET /questions/{id}`). 목록 링크, 루트 리다이렉트(`redirect:/questions`), 테스트 주소도 바꿨다
- 이유: 강사식 REST 규칙. 자원 이름은 복수형 명사로 쓰고 `list`, `detail` 같은 동작은 주소에 넣지 않는다. 동작은 HTTP 메서드(GET 조회, POST 생성, PUT 수정, DELETE 삭제)로 구분한다
- 결과: `/` → `/questions`로 302 리다이렉트, `/questions`에서 목록, `/questions/{id}`에서 상세, 목록의 링크도 `href="/questions/{id}"`. 테스트 11개 통과
- 메모: 책은 끝까지 `/question/list`, `/question/detail/{id}`를 쓴다. 이후 주소는 모두 이 REST 규칙으로 바꿔 적용한다 (답변 `POST /questions/{id}/answers`, 수정·삭제 `PUT`/`DELETE`)

### 012-01 답변, AnswerService.create 답변 저장
- 작업: `domain.question.answer.service.AnswerService` 추가. `create(question, content)`가 `question.addAnswer(content)`로 답변을 만들고 `answerRepository.save()`. `@Transactional`. `AnswerServiceTests` 추가
- 이유: 답변 저장 처리 (책 2-12). 책처럼 `new Answer()` 후 `setQuestion`, `setCreateDate`를 직접 하지 않고, 004-03의 `addAnswer`(양쪽 관계를 한 번에 맞춤)와 004-02 Auditing(생성일시 자동)을 쓴다
- 결과: 저장된 답변에 id와 생성일시가 채워지고, `answer.getQuestion()`은 그 질문, `question.getAnswerList()`에는 그 답변이 들어 있다. 테스트 12개 통과

### 012-02 답변, AnswerController 답변 등록 요청 처리
- 작업: `domain.question.answer.controller.AnswerController` 추가. `@RequestMapping("/questions/{questionId}/answers")` + `@PostMapping`. `@PathVariable questionId`, `@RequestParam content`를 받아 질문을 찾고 `answerService.create` 후 `redirect:/questions/{questionId}`
- 이유: 상세 화면의 답변 폼이 보낼 요청을 받기 위해 (책 2-12). 답변은 질문에 속한 자원이라 질문 주소 아래에 둔다(강사식, 책은 `POST /answer/create/{id}`)
- 결과: `POST /questions/{id}/answers` (content=답변입니다.) → 302로 `/questions/{id}` 이동, 질문의 답변 목록에 "답변입니다."가 저장됐다. 테스트 13개 통과
- 메모: 저장 후 화면을 바로 그리지 않고 리다이렉트하는 것을 PRG(Post-Redirect-Get)라 한다. 새로고침해도 POST가 다시 가지 않아 중복 등록이 없다

### 012-03 답변, 상세 화면에 답변 입력 폼
- 작업: `question_detail.html`에 `<form th:action="@{|/questions/${question.id}/answers|}" method="post">`, `<textarea name="content">`, 답변등록 버튼
- 이유: 상세 화면에서 답변을 입력해 012-02로 보내기 위해 (책 2-12)
- 결과: 상세 응답 HTML에 `action="/questions/{id}/answers"` 폼이 생겼다. 화면에서 답변을 입력하고 등록할 수 있다
- 메모: 입력칸의 `name="content"`가 컨트롤러의 `@RequestParam String content`와 이름이 같아야 값이 들어간다. `th:action`을 쓰면 021 시큐리티에서 CSRF 토큰이 자동으로 들어간다

### 012-04 답변, 상세 화면에 답변 개수와 목록 표시
- 작업: `question_detail.html`에 `${#lists.size(question.answerList)}개의 답변이 있습니다.`와 `th:each`로 답변 내용 목록(`<ul><li>`)
- 이유: 등록된 답변을 상세 화면에서 보기 위해 (책 2-12)
- 결과: 답변 1개 달린 질문의 상세 화면에 "1개의 답변이 있습니다."와 답변 내용 "스프링부트 게시판입니다."가 나온다. 등록 → 리다이렉트 → 새 답변이 보이는 흐름 완성. 테스트 13개 통과
- 메모: `answerList`는 004-03에서 LAZY로 정해 템플릿에서 처음 쓸 때 조회된다. 화면을 그리는 동안 DB 연결이 열려 있는 것은 `open-in-view` 기본값(003-03 경고) 덕분이다

### 013-01 웹 디자인, 공통 CSS 파일 static/resource/global.css
- 작업: `src/main/resources/static/resource/global.css` 추가(`textarea { width: 100%; }`, `input[type=submit] { margin-top: 10px; }`). `/resource/global.css` 제공을 확인하는 `StaticResourceTests` 추가
- 이유: 화면 스타일을 한 파일에 모으려고 (책 2-13의 `style.css`). 강사식으로 정적 파일은 `static/resource` 폴더에 모아 컨트롤러 주소와 섞이지 않게 한다
- 결과: `GET /resource/global.css` → 200, 응답에 `textarea` 규칙이 들어 있다. 아직 어느 화면도 불러오지 않아 모양 변화는 없다. 테스트 14개 통과
- 메모: `static` 폴더 파일은 컨트롤러 없이 폴더 경로 그대로 주소가 된다

### 013-02 웹 디자인, 목록·상세 템플릿에 공통 CSS 연결
- 작업: `question_list.html`, `question_detail.html`의 `<head>`에 `<link rel="stylesheet" th:href="@{/resource/global.css}">`
- 이유: 013-01 스타일을 실제 화면에 적용 (책 2-13)
- 결과: 두 화면 HTML에 `href="/resource/global.css"`가 들어가, 답변 입력칸이 가로로 꽉 차고 버튼 위에 여백이 생긴다. 테스트 14개 통과
- 메모: 같은 `<head>` 내용을 템플릿마다 반복하고 있다. 015 레이아웃에서 한곳으로 모은다

### 014-01 부트스트랩, 부트스트랩 CSS를 cdnjs CDN으로 연결
- 작업: 두 템플릿 `<head>`에 `https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css` 링크를 `global.css`보다 먼저 추가
- 이유: 미리 만들어진 스타일 클래스(`table`, `btn`, `card` 등)를 쓰려고 (책 2-14). 책은 파일을 내려받아 `static`에 넣지만 강사식은 CDN, 버전은 5.3.3으로 고정
- 결과: 화면에 부트스트랩 기본 글꼴·여백이 적용된다. 목록 HTML에 부트스트랩 링크 포함 확인. 테스트 14개 통과
- 메모: CSS는 나중에 불러온 것이 앞의 규칙을 덮어쓴다. 우리 `global.css`가 부트스트랩을 고칠 수 있게 순서를 정했다

### 014-02 부트스트랩, Pretendard 한글 폰트 적용
- 작업: 두 템플릿에 Pretendard v1.3.9 CSS(jsDelivr CDN) 링크, `global.css`에 `body { font-family: "Pretendard", -apple-system, ..., sans-serif; }`
- 이유: 기본 글꼴보다 읽기 좋은 한글 웹폰트 (강사식)
- 결과: 화면 전체 글꼴이 Pretendard로 바뀐다. `global.css` 응답에 `Pretendard` 지정 확인. 테스트 14개 통과
- 메모: 폰트 파일을 불러오는 것(`link`)과 실제로 쓰는 것(`font-family`)은 따로다. 뒤쪽 글꼴은 폰트를 못 받았을 때의 대체 글꼴

### 014-03 부트스트랩, 목록 화면에 부트스트랩 클래스 적용
- 작업: 목록을 `<div class="container my-3">`로 감싸고, 표에 `class="table"`, 머리줄에 `class="table-dark"`
- 이유: 목록 화면을 부트스트랩 표 모양으로 정리 (책 2-14)
- 결과: 목록이 화면 가운데 고정 폭으로 놓이고, 줄마다 구분선이 생기며 머리줄(제목, 작성일시)이 어두운 배경이 된다. 목록 HTML에 `<table class="table">` 확인. 테스트 14개 통과

### 014-04 부트스트랩, 상세 화면에 부트스트랩 카드 적용
- 작업: 상세를 `container my-3`으로 감싸고, 제목 `h2.border-bottom`, 질문 내용과 각 답변을 `card`로, 답변 목록 `<ul><li>`를 답변마다 카드로 변경, 입력칸 `form-control`, 버튼 `btn btn-primary`, 내용에 `white-space: pre-line`
- 이유: 상세 화면을 부트스트랩 카드 모양으로 정리 (책 2-14)
- 결과: 질문 내용과 답변이 테두리 있는 상자로 나뉘어 보이고, 입력한 줄바꿈이 화면에서도 줄바꿈으로 나온다. 답변등록 버튼이 파란 버튼이 됐다. 상세 HTML에 `card my-3`, `btn btn-primary` 확인. 테스트 14개 통과

### 015-01 레이아웃, 타임리프 레이아웃 다이얼렉트 의존성 추가
- 작업: `build.gradle.kts`에 `nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect` 추가 (버전은 Spring Boot 의존성 관리)
- 이유: 화면마다 반복되는 `<head>`와 바깥 틀을 공통 틀 하나에 모으려고 (강사식). 책 2-15는 `th:replace`로 `<head>` 조각만 나눈다
- 결과: 라이브러리가 들어와 타임리프에서 `layout:` 문법을 쓸 수 있게 됐다(스프링이 자동 등록). 아직 쓰는 화면은 없다. 테스트 14개 통과

### 015-02 레이아웃, 공통 틀 layout.html 추가
- 작업: `templates/layout.html` 추가. `<head>`에 부트스트랩·Pretendard·`global.css` 링크와 기본 제목 `SBB`, `<body>`에 각 화면 내용이 들어갈 `<div layout:fragment="content">`
- 이유: 화면마다 반복되던 `<head>` 3줄을 한 파일로 모으려고 (강사식)
- 결과: 공통 틀 파일 생성. 화면 적용은 015-03, 015-04. 테스트 14개 통과

### 015-03 레이아웃, 목록 화면이 layout.html 사용
- 작업: `question_list.html`의 `<html>`에 `layout:decorate="~{layout}"`, `<head>`에는 제목만 남기고 링크 3줄 삭제, 내용 `div`에 `layout:fragment="content"`
- 이유: 목록 화면의 공통 부분을 레이아웃에 맡기려고 (강사식)
- 결과: 목록 화면 파일에서 링크를 지웠는데도 응답 HTML에는 부트스트랩·공통 CSS 링크가 그대로 들어 있다(013-02, 014-01 테스트가 고치지 않고 통과). 레이아웃과 화면이 합쳐져 나온다는 뜻이다. 테스트 14개 통과

### 015-04 레이아웃, 상세 화면이 layout.html 사용
- 작업: `question_detail.html`도 `layout:decorate="~{layout}"`, 제목만 남기고 링크 3줄 삭제, 바깥 `container` `div`에 `layout:fragment="content"`
- 이유: 이제 공통 `<head>`는 `layout.html` 한 곳에서만 관리하려고
- 결과: 상세 응답 HTML에도 레이아웃에서 온 부트스트랩 링크가 들어 있다(테스트 추가). 화면 모양은 전과 같다. 테스트 14개 통과

### 015-05 레이아웃, 화면 제목 패턴 layout:title-pattern
- 작업: `layout.html`의 `<title>`에 `layout:title-pattern="$CONTENT_TITLE | $LAYOUT_TITLE"`
- 이유: 브라우저 탭 제목을 "화면 이름 | SBB"로 통일하려고 (강사식). 화면은 자기 이름만 적으면 된다
- 결과: 목록 화면 탭 제목이 `질문 목록 | SBB`, 상세는 `질문 상세 | SBB`가 된다. 목록 응답에 `<title>질문 목록 | SBB</title>` 확인. 테스트 14개 통과

### 015-06 레이아웃, 공통 자바스크립트 global.js
- 작업: `static/resource/global.js` 추가(화면이 준비되면 함수를 실행하는 `onReady(fn)` 도우미), `layout.html`에 `<script defer th:src="@{/resource/global.js}">`
- 이유: 여러 화면이 함께 쓸 JS를 한 곳에 모으려고 (강사식). 016 첫 오류 칸 focus, 025 DELETE 링크 처리가 이 파일에 들어간다
- 결과: `GET /resource/global.js` → 200, 모든 화면 HTML에 `src="/resource/global.js"` 스크립트가 들어간다. 테스트 15개 통과
- 메모: `defer`는 HTML을 다 읽은 뒤 실행해 화면 표시를 늦추지 않는다

### 016-01 질문 등록, QuestionService.create 질문 저장
- 작업: `QuestionService.create(subject, content)` 추가. 새 `Question`에 제목·내용을 넣고 `save`, 저장된 질문을 돌려준다. `@Transactional`
- 이유: 질문 등록 화면이 쓸 저장 처리 (책 2-16). 생성일시는 004-02 Auditing이 채운다(책은 `setCreateDate` 직접 호출)
- 결과: `create("새 질문", "새 내용")` 후 id·생성일시가 채워지고 `getQuestion`으로 다시 조회하면 제목이 같다. 테스트 16개 통과

### 016-02 질문 등록, 등록 폼 화면 GET /questions/new
- 작업: `QuestionController.showCreate()` (`GET /questions/new`) → 새 템플릿 `question_form.html`(레이아웃 사용, 제목 `input name="subject"`, 내용 `textarea name="content"`, `POST /questions`로 보내는 폼)
- 이유: 질문 입력 화면 (책 2-16). REST 규칙으로 생성 입력 화면은 `/questions/new`, 저장은 `POST /questions` (책은 `/question/create` 하나로 GET·POST)
- 결과: `GET /questions/new` → 200, 탭 제목 `질문 등록 | SBB`, 폼 `action="/questions"`. 아직 POST를 받는 곳이 없어 저장은 안 된다. 테스트 17개 통과
- 메모: `/new`와 `/{id}`가 겹쳐 보여도 스프링은 글자 그대로인 경로를 변수 경로보다 먼저 맞춘다

### 016-03 질문 등록, 저장 요청 POST /questions
- 작업: `QuestionController.create()` (`@PostMapping` = `POST /questions`), `@RequestParam subject`, `content`를 받아 `questionService.create` 후 `redirect:/questions`
- 이유: 등록 폼이 보낸 질문을 저장 (책 2-16). 목록과 같은 자원 주소에 POST = 생성(REST)
- 결과: `POST /questions` (subject=새 질문, content=새 내용) → 302로 `/questions` 이동, DB에서 `findBySubject("새 질문")`으로 저장 확인. 테스트 18개 통과
- 메모: 이때는 제목이 비어도 저장된다. 016-05에서 검증을 넣는다

### 016-04 질문 등록, 목록에 질문 등록하기 버튼
- 작업: 목록 표 아래에 `<a th:href="@{/questions/new}" class="btn btn-primary">질문 등록하기</a>`
- 이유: 목록에서 등록 화면으로 가는 길 (책 2-16)
- 결과: 목록 화면에 파란 "질문 등록하기" 버튼이 생겼다(HTML에 `href="/questions/new"`). 목록 → 등록 폼 → 저장 → 목록에 새 질문, 흐름이 이어진다. 테스트 18개 통과

### 016-05 질문 등록, record 폼 QuestionForm과 서버 검증
- 작업: `spring-boot-starter-validation` 추가. `domain.question.question.dto.QuestionForm` record(제목 `@NotBlank` + `@Size(max = 200)`, 내용 `@NotBlank`, 각각 한글 메시지). `create()`가 `@RequestParam` 두 개 대신 `@Valid QuestionForm` + `BindingResult`로 받고, 오류가 있으면 저장하지 않고 `question_form`을 다시 보여 준다
- 이유: 빈 제목·내용, 200자 넘는 제목을 서버에서 막으려고 (책 2-16). 강사식으로 폼은 `@Getter/@Setter` 클래스 대신 `record`
- 결과: 제목에 공백(" ")을 보내면 200 + 등록 폼이 다시 나오고 질문 개수가 늘지 않는다. 정상 입력은 그대로 저장된다. 테스트 19개 통과
- 메모: `BindingResult`는 반드시 `@Valid` 객체 바로 뒤에 둬야 한다. 없으면 검증 실패가 400 오류로 끝난다

### 016-06 질문 등록, th:object·th:field와 필드별 오류 메시지
- 작업: 등록 폼에 `th:object="${questionForm}"`, 입력칸 `th:field="*{subject}"`/`"*{content}"`, `th:errorclass="is-invalid"`, 칸 아래 `<div class="invalid-feedback" th:errors="*{...}">`. 폼 화면 GET이 빈 `QuestionForm`을 인자로 받아 모델에 담는다
- 이유: 어느 칸이 왜 틀렸는지 칸 바로 아래 보여 주고, 다시 열린 폼에 입력값을 남기려고 (책 2-16은 오류를 폼 위에 한꺼번에, 강사식은 칸마다)
- 결과: 제목을 비우고 저장하면 제목 칸이 빨간 테두리가 되고 아래에 "제목은 필수항목입니다."가 나오며, 입력했던 내용은 textarea에 그대로 남아 있다. 테스트 19개 통과
- 메모: `record`에도 `th:field`가 정상 동작함을 테스트로 확인했다

### 016-07 질문 등록, 브라우저 검증 required·maxlength
- 작업: 제목 입력칸에 `required maxlength="200"`, 내용 textarea에 `required`
- 이유: 서버에 보내기 전에 브라우저에서 먼저 막아 빠르게 알려 주려고. 서버 검증과 같은 규칙으로 양쪽에서 검증(강사식)
- 결과: 칸을 비우고 저장을 누르면 브라우저가 전송을 막고 "이 입력란을 작성하세요" 말풍선을 띄운다. 제목은 200자 넘게 입력되지 않는다. 폼 HTML에 `required`, `maxlength="200"` 확인. 테스트 19개 통과
- 메모: 브라우저 검증은 개발자 도구나 직접 요청으로 우회된다. 공백만 있는 값도 `required`로는 못 막는다. 진짜 방어선은 서버 검증(016-05)

### 016-08 질문 등록, 첫 오류 입력칸에 자동 focus
- 작업: `global.js`에 화면 준비 후 `document.querySelector(".is-invalid")`로 첫 오류 칸을 찾아 `focus()`하는 코드
- 이유: 검증 실패로 폼이 다시 열리면 고칠 칸으로 바로 커서를 옮기려고 (강사식). 공통 JS라 앞으로 만들 모든 폼에 적용된다
- 결과: 서버 검증 실패로 다시 열린 폼에서 첫 번째 빨간 칸에 커서가 가 있다. `global.js` 응답에 해당 코드 포함 확인. 테스트 19개 통과
- 메모: MockMvc는 JS를 실행하지 않아 실제 커서 이동은 브라우저에서 확인할 부분이다

### 016-09 질문 등록, 답변 AnswerForm과 서버 검증
- 작업: `domain.question.answer.dto.AnswerForm` record(내용 `@NotBlank`). `AnswerController.create()`가 `@Valid AnswerForm` + `BindingResult`로 받고, 오류면 질문을 모델에 담아 `question_detail`을 다시 그린다
- 이유: 빈 답변 저장을 서버에서 막으려고 (책 2-16). 질문과 같은 방식으로 통일
- 결과: 공백 답변을 보내면 200 + 상세 화면이 다시 나오고 답변이 저장되지 않는다. 정상 답변 등록(012-02)은 그대로 동작. 테스트 20개 통과
- 메모: 상세 화면은 `${question}`이 있어야 그릴 수 있어 오류일 때 직접 모델에 담는다. 질문 조회를 먼저 해서 없는 질문이면 404가 먼저 난다

### 016-10 질문 등록, 답변 폼에 th:object·오류 표시·required
- 작업: 상세 화면 답변 폼에 `th:object="${answerForm}"`, `th:field="*{content}"`, `th:errorclass="is-invalid"`, `th:errors`, `required`. 상세 GET이 빈 `AnswerForm`을 받아 모델에 담는다
- 이유: 답변 폼도 질문 폼과 같이 칸 아래 오류 표시, 입력값 유지, 브라우저 검증 (책 2-16)
- 결과: 공백 답변을 보내면 상세 화면 답변 칸이 빨간 테두리가 되고 "내용은 필수항목입니다."가 나온다. 016-08 덕분에 그 칸에 커서가 간다. 테스트 20개 통과

### 017-01 내비게이션 바, navbar.html 프래그먼트 파일
- 작업: `templates/navbar.html` 추가. `th:fragment="navbar"`인 부트스트랩 `navbar`(로고 `SBB` → `/`, 좁은 화면용 햄버거 버튼, "질문 목록" 메뉴)
- 이유: 모든 화면 위에 공통 메뉴를 두려고 (책 3-01). 강사식으로 레이아웃에 직접 쓰지 않고 조각 파일로 분리
- 결과: 조각 파일 생성. 아직 붙인 곳이 없어 화면 변화는 없다. 테스트 20개 통과
- 메모: 로그인·회원가입 메뉴는 021~023에서 그 기능을 만든 뒤 넣는다

### 017-02 내비게이션 바, 레이아웃에 navbar 붙이기
- 작업: `layout.html`의 `<body>` 맨 위에 `<nav th:replace="~{navbar :: navbar}"></nav>`
- 이유: 레이아웃에 한 번 붙이면 레이아웃을 쓰는 모든 화면에 메뉴가 나온다 (책 3-01)
- 결과: 목록·상세·등록 폼 맨 위에 "SBB" 로고와 "질문 목록" 메뉴가 있는 바가 생겼다. 목록 HTML에 `navbar-brand` 확인. 테스트 20개 통과
- 메모: `th:insert`는 태그 안에 넣어 `<nav>` 안에 `<nav>`가 생기므로 `th:replace`를 쓴다

### 017-03 내비게이션 바, 부트스트랩 JS로 햄버거 메뉴 동작
- 작업: `layout.html`에 `bootstrap.bundle.min.js`(5.3.3, cdnjs, `defer`)
- 이유: 부트스트랩 CSS는 모양만 만들고, 버튼을 누르면 메뉴가 펼쳐지는 동작은 JS가 한다 (책 3-01)
- 결과: 좁은 화면에서 햄버거 버튼을 누르면 메뉴가 펼쳐지고 접힌다. 모든 화면 HTML에 부트스트랩 JS 포함 확인. 테스트 20개 통과
- 메모: `bundle`은 Popper까지 합친 파일. CSS와 같은 버전(5.3.3)으로 맞췄다

### 018-01 페이징, 서비스가 한 페이지(10개)씩 조회
- 작업: `QuestionService.getList()` → `getList(int page)`가 `PageRequest.of(page, 10)`과 `findAll(Pageable)`로 `Page<Question>`을 돌려준다. 컨트롤러는 우선 `getList(0)`
- 이유: 질문이 많아지면 전체를 한 번에 읽는 것이 느리고 화면도 길어진다 (책 3-02)
- 결과: 질문 12개일 때 0페이지 10개, 1페이지 2개, 전체 개수 12, 전체 페이지 2. 목록 화면에는 처음 10개만 나온다. SQL 로그에 `offset`/`limit` 쿼리와 `count` 쿼리가 함께 나간다. 테스트 20개 통과
- 메모: `Page`에는 목록(`getContent()`) 외에 전체 개수, 전체 페이지 수, 이전/다음 여부가 들어 있다

### 018-02 페이징, 최신 글이 먼저 오도록 작성일시 내림차순 정렬
- 작업: `PageRequest.of(page, 10, Sort.by(Sort.Order.desc("createDate")))`
- 이유: 게시판은 최신 글을 먼저 보여 줘야 한다 (책 3-02)
- 결과: 질문1~질문12를 순서대로 저장했을 때 첫 페이지 맨 위가 질문12, 마지막 페이지 맨 아래가 질문1. 목록 화면 맨 위에 가장 최근 질문이 온다. 테스트 20개 통과
- 메모: 정렬은 DB의 `order by`로 해야 한다. 10개를 자른 뒤 자바에서 정렬하면 그 10개 안에서만 순서가 바뀐다

### 018-03 페이징, 주소의 ?page 값으로 페이지 선택 (1부터)
- 작업: `list(@RequestParam(defaultValue = "1") int page, ...)`가 `getList(Math.max(page, 1) - 1)` 호출. 모델 이름 `questionList` → `paging`, 템플릿 `th:each`도 `${paging}`
- 이유: 주소로 원하는 페이지를 보려고 (책 3-02). 강사식으로 사용자에게 보이는 번호는 1부터(책은 0부터). 0 이하 값은 1페이지로
- 결과: `/questions`와 `/questions?page=1`은 최신 10개, `/questions?page=2`는 11번째부터 보여 준다. 가장 오래된 질문이 page=2에는 있고 page=1에는 없음을 확인. 테스트 21개 통과

### 018-04 페이징, 목록 아래 페이지 이동 버튼
- 작업: `question_list.html`에 부트스트랩 `pagination`. "이전"(첫 페이지면 `disabled`), 현재 페이지 앞뒤 5개 번호(현재는 `active`), "다음"(마지막이면 `disabled`). 링크는 `@{/questions(page=...)}`, 질문이 없으면 버튼 줄을 그리지 않는다
- 이유: 화면에서 페이지를 이동하려고 (책 3-02)
- 결과: 목록 아래 가운데에 `이전 1 2 다음` 버튼이 생기고, 현재 페이지 번호가 파란 배경, 1페이지에서는 "이전"이 회색으로 눌리지 않는다. HTML에 `href="/questions?page=2"`, `page-item active`, `page-item disabled` 확인. 테스트 21개 통과
- 메모: `paging.number`는 0부터라 화면 번호는 `number + 1`, 이전은 `number`, 다음은 `number + 2`


### 018-05 페이징, 페이징 확인용 샘플 질문 300개
- 작업: `BaseInitData`에 `work2()` 추가. 질문이 100개 미만일 때만 `"테스트 데이터입니다:[001]"`~`"[300]"`(내용 `"내용무"`) 300개를 저장. 실행기가 `work()` 다음에 `self.work2()`를 부른다
- 이유: 018-01~04 페이징을 화면에서 확인하려면 여러 페이지 분량의 데이터가 필요하다 (책 3-02는 테스트 코드로 300개를 넣는다, 강사식은 dev 서버 시작 때 `BaseInitData`에서). 100개 이상이면 건너뛰어 재시작할 때마다 300개씩 쌓이지 않게 했다
- 결과: `bootRun` 로그에 `테스트 데이터입니다:[001]`부터 `[300]`까지 insert 바인딩 값이 찍혔다. 질문은 기존 2개 + 300개 = 302개, 31페이지. `/questions` 첫 화면 맨 위에 최신 테스트 데이터가 오고 아래에 페이지 버튼 1~6이 나온다(018-04 "앞뒤 5개"). `/questions?page=31`(마지막 페이지)에는 처음 넣은 `스프링부트 모델 질문입니다.`, `sbb가 무엇인가요?` 2개가 나오고 버튼은 26~31. `gradlew test` 통과(테스트는 test 프로파일이라 영향 없음)
- 메모 1: `work()`와 나눈 이유: `db_dev`에 이미 샘플 2개가 있어 `work()`는 건너뛰지만, 이 300개는 새로 필요했다
- 메모 2: 한 번에 빠르게 저장해서 몇몇 질문은 작성일시가 같다. 그래서 1페이지 맨 위가 `[300]`이 아니라 `[299]`로 나왔다. 작성일시가 같을 때의 순서는 정해져 있지 않다

### 018-06 페이징, 작성일시가 같을 때 id 내림차순으로 순서 고정
- 작업: `getList(page)`의 정렬을 `Sort.by(Sort.Order.desc("createDate"), Sort.Order.desc("id"))`로 바꿔 두 번째 기준(id 내림차순)을 추가
- 이유: 018-05에서 한 번에 저장한 질문 몇 개는 작성일시가 마이크로초까지 같아, 1페이지 맨 위가 `[300]`이 아니라 `[299]`였다. 같은 값끼리의 순서는 DB가 정하므로 실행할 때마다 달라질 수 있다. 절대 겹치지 않는 id를 두 번째 기준으로 두면 순서가 항상 같다 (책에는 없는 보완)
- 결과: 서버를 다시 켜지 않아도 devtools가 바뀐 코드를 다시 읽었고, `/questions` 1페이지가 `테스트 데이터입니다:[300]`, `[299]`, `[298]` 순서로 나온다. `gradlew test` 21개 통과
- 메모: 정렬 기준이 겹칠 수 있을 때는 마지막에 고유한 값(id)을 붙이는 것이 페이징의 기본이다. 순서가 흔들리면 같은 질문이 두 페이지에 걸쳐 나오거나 빠질 수 있다

### 019-01 게시물 번호, 목록에 전체 개수 기준 번호 칸
- 작업: 목록 머리줄에 `번호` 칸을 넣고, 반복을 `th:each="question, loop : ${paging}"`로 바꿔 번호를 `paging.totalElements - (paging.number * paging.size) - loop.index`로 계산. 칸은 가운데 정렬(`text-center`), 제목 칸은 넓게(`width:50%`) 왼쪽 정렬(`text-start`)
- 이유: 몇 번째 글인지 보여 주는 번호 (책 3-03). 질문 id를 그대로 쓰면 삭제된 글 번호가 비고 페이지와 상관없는 숫자가 된다. 전체 개수로 계산하면 최신 글이 가장 큰 번호, 가장 오래된 글이 1번이 된다
- 결과: 질문 302개인 dev 서버에서 `/questions` 1페이지 번호가 302, 301, 300…, 마지막 `?page=31`은 2, 1. 테스트(질문 12개)에서 1페이지 맨 위 `12`, 2페이지 `2`, `1` 확인. `gradlew test` 21개 통과
- 메모: `loop`는 타임리프 반복 상태 변수다. `index`(0부터), `count`(1부터), `first`, `last` 등을 쓸 수 있다

### 020-01 답변 개수, 목록 제목 옆에 답변 개수 표시
- 작업: 목록의 제목 링크 옆에 `<span class="text-danger small ms-2" th:text="${#lists.size(question.answerList)}">`. 답변이 0개면 `th:if`로 그리지 않는다
- 이유: 목록에서 답변이 달린 질문을 바로 알아보게 (책 3-04). 004의 `@OneToMany answerList` 컬렉션 크기를 그대로 쓴다
- 결과: dev 서버 `/questions?page=31`에서 답변이 하나씩 달린 샘플 질문 2개 제목 옆에 빨간 `1`이 나온다. 답변이 없는 1페이지 테스트 데이터에는 아무것도 붙지 않는다. 테스트에서 `<span class="text-danger small ms-2">1</span>` 확인, `gradlew test` 21개 통과
- 메모: 목록 10개 질문마다 `answerList`를 처음 쓸 때 답변 조회 쿼리가 하나씩 더 나간다(LAZY라서). 이 N+1 문제는 020-02에서 줄인다

### 020-02 답변 개수, default_batch_fetch_size로 답변 조회 N+1 줄이기
- 작업: `application.yaml`에 `spring.jpa.properties.hibernate.default_batch_fetch_size: 100`
- 이유: 020-01부터 목록의 질문마다 `answerList`를 쓰면서, 질문 10개면 답변 조회 쿼리가 10번 따로 나갔다(N+1 문제). 이 설정은 LAZY 컬렉션을 처음 쓸 때 같은 화면의 다른 질문들 것까지 최대 100개를 `in (...)` 한 번으로 모아 조회한다 (강사식, 책에는 없음)
- 결과: dev 서버에서 `/questions?page=1` 요청 한 번에 나가는 SQL이 12개(질문 목록 1 + 전체 개수 1 + 답변 10)에서 3개(질문 목록 1 + 전체 개수 1 + 답변 1)로 줄었다. 로그에 `from answer ... where question_id in (?, ?, ...)` 쿼리가 찍힌다. 화면 결과(답변 개수 표시)는 같고 `gradlew test` 21개 통과
- 메모: `in (...)`의 `?`가 100개 가까이 찍히는 것은 Hibernate가 쿼리 모양을 몇 가지로 고정해 재사용하려고 빈칸을 채우기 때문이다

### 021-01 스프링 시큐리티, 의존성과 모든 요청 허용 설정
- 작업: `spring-boot-starter-security`(테스트용 `spring-boot-starter-security-test`) 추가. `global.security.SecurityConfig`에 `SecurityFilterChain` 빈을 만들고 `requestMatchers("/**").permitAll()`로 모든 주소를 로그인 없이 허용. 컨트롤러 테스트의 POST 요청 4곳에 `.with(csrf())`를 붙이고, 토큰 없는 POST가 막히는지 보는 테스트를 추가
- 이유: 022 회원가입, 023 로그인, 024 글쓴이 권한의 기반 (책 3-05). 시큐리티는 넣기만 하면 모든 주소를 로그인 필요로 막고 자동 로그인 화면을 띄운다. 아직 회원 기능이 없으므로 우선 전부 열어 두고, 필요한 곳만 하나씩 막는다 (강사식)
- 결과: 화면(GET)은 전처럼 로그인 없이 열린다. 시큐리티를 넣은 직후 질문·답변 등록 POST 테스트 4개가 403(거부)으로 실패했다. 시큐리티는 CSRF 방어가 기본으로 켜져 있어, 토큰 없이 들어온 POST를 막기 때문이다. 테스트 요청에 `csrf()`로 토큰을 붙이자 통과했다. 토큰 없는 `POST /questions`는 403이고 질문이 저장되지 않는다. `gradlew test` 22개 통과
- 메모 1: CSRF(사이트 간 요청 위조)는 다른 사이트가 사용자 브라우저를 이용해 몰래 POST를 보내는 공격이다. 서버가 내려 준 토큰이 있는 요청만 받아서 막는다
- 메모 2: 실제 브라우저의 폼은 012-03, 016-02에서 `th:action`을 써 두었기 때문에 타임리프가 토큰을 자동으로 넣는다(021-03에서 확인)

### 021-02 스프링 시큐리티, H2 콘솔 CSRF 예외와 같은 사이트 프레임 허용
- 작업: `SecurityConfig`에 `.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))`와 `.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))`를 추가. 응답 헤더 테스트 추가
- 이유: 021-01 뒤 H2 콘솔이 쓸 수 없게 됐다 (책 3-05). 콘솔은 우리 폼이 아니어서 CSRF 토큰을 보내지 못해 로그인 POST가 403으로 막혔고, 콘솔 화면은 frameset(여러 프레임)인데 시큐리티 기본 헤더 `X-Frame-Options: DENY`가 모든 프레임을 막았다
- 결과: dev 서버를 다시 켜서 비교했다. 수정 전 `X-Frame-Options=DENY`, 콘솔 로그인 `POST /h2-console/login.do` → 403. 수정 후 `X-Frame-Options=SAMEORIGIN`, 같은 로그인 요청 → 200과 frameset 화면(로그인 성공). 질문 목록 등 다른 화면은 그대로 200. `gradlew test` 23개 통과
- 메모: CSRF 예외는 `/h2-console/**`에만 줬다. 우리 화면의 POST는 계속 토큰을 검사한다. `SAMEORIGIN`은 같은 사이트 안의 프레임만 허용하므로, 다른 사이트가 우리 화면을 몰래 프레임에 넣는 클릭재킹은 계속 막힌다

### 021-03 스프링 시큐리티, th:action 폼의 CSRF 토큰 자동 삽입 확인
- 작업: 질문 등록 폼(`/questions/new`)과 상세 화면 답변 폼 HTML에 `<input type="hidden" name="_csrf" ...>`가 들어 있는지 보는 테스트 추가. 템플릿은 고치지 않았다
- 이유: 테스트는 `csrf()`로 토큰을 붙였지만, 실제 브라우저 폼도 토큰을 보내는지 확인해야 한다 (책 3-05). 012-03, 016-02에서 폼 주소를 `action`이 아닌 `th:action`으로 써 두었기 때문에, 시큐리티가 있으면 타임리프가 토큰 숨은 입력칸을 자동으로 넣는다
- 결과: 두 폼 모두 `_csrf` 숨은 입력칸이 있다. dev 서버에서 등록 폼이 준 토큰(96자)과 함께 `POST /questions`를 보내면 저장되어 목록에 나오고, 토큰 없이 보내면 403. `gradlew test` 24개 통과
- 메모 1: 일반 `action="..."`으로 썼다면 토큰이 들어가지 않아 브라우저에서 등록할 때마다 403이 났을 것이다
- 메모 2: 확인하느라 dev DB에 "CSRF 확인 질문", "CSRF 확인 질문2" 두 개가 저장됐다

### 022-01 회원가입, 회원 엔티티 Member
- 작업: `domain.member.member.entity.Member`(`BaseEntity` 상속). `username`(`@Column(unique = true, length = 25)`), `password`, `email`(`@Column(unique = true)`)
- 이유: 회원 정보를 저장할 테이블 (책 3-06). 책은 `user`가 DB 예약어라 `SiteUser`라고 부르지만, 강사식으로 `Member`라 부르고 질문과 같은 `domain.member.member.entity` 구조에 둔다
- 결과: 테스트 시작 로그에 `create table member (id integer generated by default as identity, create_date timestamp(6), modify_date timestamp(6), username varchar(25) unique, email varchar(255) unique, password varchar(255), primary key (id))`가 찍힌다. `username`, `email`에 `unique`가 붙었다. `gradlew test` 24개 통과
- 메모: 유니크 제약은 DB가 같은 값의 두 번째 저장을 거부하게 한다. 화면에서 친절한 메시지를 보여 주는 처리는 022-07에서 한다

### 022-02 회원가입, MemberRepository와 findByUsername
- 작업: `domain.member.member.repository.MemberRepository`(`JpaRepository<Member, Integer>`)와 `Optional<Member> findByUsername(String username)`. 테스트 `MemberRepositoryTests`
- 이유: 회원을 저장하고, 023 로그인에서 입력한 아이디로 회원을 찾기 위해 (책 3-06의 `UserRepository`)
- 결과: 저장한 `user1`을 `findByUsername("user1")`로 찾으면 이메일이 맞고, 없는 아이디는 빈 `Optional`. 같은 아이디로 두 번 저장(`saveAndFlush`)하면 022-01의 유니크 제약 때문에 `DataIntegrityViolationException`이 난다. `gradlew test` 26개 통과
- 메모: `save`는 바로 DB에 보내지 않을 수 있어서, 중복 오류를 그 자리에서 확인하려고 즉시 반영하는 `saveAndFlush`를 썼다

### 022-03 회원가입, 비밀번호 암호화 PasswordEncoder(BCrypt) 빈
- 작업: `SecurityConfig`에 `@Bean PasswordEncoder passwordEncoder()`(`new BCryptPasswordEncoder()`). 테스트 `PasswordEncoderTests`
- 이유: 비밀번호를 평문으로 저장하면 DB가 유출됐을 때 그대로 드러난다 (책 3-06). 빈으로 등록해 가입(022-04)에서 암호화하고 로그인(023)에서 같은 도구로 비교한다
- 결과: `"1234"`를 두 번 암호화하면 둘 다 `$2a$`로 시작하는 서로 다른 60자 문자열이 나온다. `matches("1234", 암호)`는 true, `matches("4321", 암호)`는 false. `gradlew test` 27개 통과
- 메모: BCrypt는 매번 무작위 솔트를 섞어서 같은 비밀번호도 결과가 다르다. 되돌릴 수 없는 해시라서 비교는 "입력값을 같은 방식으로 다시 계산해 맞춰 보는" `matches`로 한다

### 022-04 회원가입, MemberService.join 비밀번호 암호화 저장
- 작업: `domain.member.member.service.MemberService.join(username, email, password)`. `PasswordEncoder`(022-03)를 주입받아 `passwordEncoder.encode(password)`를 저장한다. `@Transactional`. 테스트 `MemberServiceTests`
- 이유: 회원가입 처리 (책 3-06의 `UserService.create`). 강사식으로 메서드 이름은 `join`. 컨트롤러가 아니라 서비스에서 암호화해서, 어디서 가입시키든 평문이 저장될 수 없게 한다
- 결과: `join("user1", "user1@test.com", "1234")` 후 id와 가입일이 채워지고, DB의 비밀번호는 `"1234"`가 아닌 BCrypt 값이며 `matches("1234", 저장값)`이 true. `gradlew test` 28개 통과

### 022-05 회원가입, 가입 폼 record MemberJoinForm과 검증 규칙
- 작업: `domain.member.member.dto.MemberJoinForm` record. `username`(`@NotBlank`, `@Size(min = 3, max = 25)`), `password1`·`password2`(`@NotBlank`), `email`(`@NotBlank`, `@Email`). 테스트 `MemberJoinFormTests`는 스프링을 띄우지 않고 `Validator`로 바로 검사
- 이유: 가입 화면의 입력값을 담고 서버에서 검증하기 위해 (책 3-06의 `UserCreateForm`). 016-05처럼 강사식 record. 아이디 최대 길이는 022-01 컬럼 길이(25)와 맞춘다
- 결과: `("user1","1234","1234","user1@test.com")`은 오류 없음. 아이디 `"ab"`와 이메일 `"not-email"`이면 `username`, `email` 두 필드에 오류. 비밀번호 칸이 비거나 공백이면 `password1`, `password2`에 오류. `gradlew test` 31개 통과
- 메모: "두 비밀번호가 같은가"는 필드 하나만 보는 어노테이션으로는 검사할 수 없어 022-07 컨트롤러에서 따로 비교한다

### 022-06 회원가입, 가입 화면 GET /members/new
- 작업: `domain.member.member.controller.MemberController`(`@RequestMapping("/members")`)의 `GET /members/new`가 빈 `MemberJoinForm`을 모델에 담아 `member_join.html`을 그린다. 템플릿은 레이아웃 사용, `th:object="${memberJoinForm}"`, 아이디·비밀번호·비밀번호 확인·이메일 칸과 칸 아래 오류, 폼 위의 글로벌 오류 자리, 브라우저 검증(`required`, 아이디 `minlength="3" maxlength="25"`, `type="email"`, `type="password"`)
- 이유: 회원가입 입력 화면 (책 3-06). REST 규칙으로 입력 화면은 `/members/new`, 처리는 `POST /members`(책은 `/user/signup` 하나로 GET·POST). 016 질문 폼에서 쓴 방식을 그대로 적용
- 결과: `GET /members/new` → 200, 탭 제목 `회원가입 | SBB`, 폼 `action="/members"`, 비밀번호 칸(`name="password1"`, 입력이 ●로 가려짐), CSRF 숨은 입력칸이 들어 있다. 아직 `POST /members`를 받는 곳이 없어 가입은 안 된다. `gradlew test` 32개 통과
- 메모: 글로벌 오류는 특정 칸이 아닌 오류(비밀번호 불일치, 이미 있는 아이디 등)다. 022-07에서 이 자리에 보여 준다

### 022-07 회원가입, 가입 처리 POST /members (불일치·중복 처리)
- 작업: `MemberController.join()`이 `@Valid MemberJoinForm` + `BindingResult`로 받는다. ① 검증 실패면 폼 다시 표시 ② `password1`과 `password2`가 다르면 `bindingResult.rejectValue("password2", ...)`로 비밀번호 확인 칸 오류 ③ `memberService.join`에서 `DataIntegrityViolationException`(022-01 유니크 제약 위반)이 나면 `bindingResult.reject(...)`로 글로벌 오류 ④ 성공하면 `redirect:/`
- 이유: 회원가입 처리 (책 3-06). 사용자가 무엇이 틀렸는지 알 수 있게, 특정 칸 문제는 그 칸 아래(rejectValue), 칸과 상관없는 문제는 폼 위(reject)에 보여 준다
- 결과: 정상 입력이면 302로 `/` 이동, `findByUsername("user1")`로 저장 확인. 비밀번호를 1234/9999로 다르게 보내면 200 + 가입 폼, "2개의 비밀번호가 일치하지 않습니다."가 나오고 저장되지 않는다. 이미 있는 `user1`로 가입하면 폼 위 빨간 상자(`alert alert-danger`)에 "이미 등록된 아이디 또는 이메일입니다."가 나온다. `gradlew test` 35개 통과
- 메모: 중복 검사를 미리 `findByUsername`으로 하지 않고 DB 유니크 제약 예외로 잡는 것은 책 방식이다. 동시에 두 명이 같은 아이디로 가입해도 DB가 하나만 받아 준다

### 022-08 회원가입, 내비게이션 바에 회원가입 메뉴
- 작업: `navbar.html`의 "질문 목록" 옆에 `/members/new`로 가는 "회원가입" 메뉴
- 이유: 어느 화면에서든 가입 화면으로 갈 수 있게 (책 3-06). 017-01에서 기능이 생긴 뒤 넣기로 미뤄 둔 메뉴
- 결과: 레이아웃을 쓰는 모든 화면 위 메뉴에 "회원가입"이 생겼다. dev 서버에서 직접 확인: 목록 화면 HTML에 `href="/members/new"`, 가입 폼 토큰과 함께 `devuser`로 가입하면 302로 `/` 이동, 같은 아이디로 다시 가입하면 200 + "이미 등록된 아이디 또는 이메일입니다." `gradlew test` 35개 통과
- 메모: dev DB에 `devuser`(비밀번호 1234) 회원이 생겼다. 023 로그인 확인에 쓴다

### 023-01 로그인, 시큐리티 회원 조회 MemberSecurityService
- 작업: `domain.member.member.service.MemberSecurityService`가 `UserDetailsService`를 구현. `loadUserByUsername(username)`이 `findByUsername`(022-02)으로 회원을 찾아 시큐리티의 `User`(아이디, 암호화된 비밀번호, 권한 목록)로 돌려준다. 없으면 `UsernameNotFoundException`. 권한은 모든 회원 `ROLE_MEMBER`, 아이디가 `admin`이면 `ROLE_ADMIN` 추가
- 이유: 로그인할 때 스프링 시큐리티가 "이 아이디의 회원 정보"를 가져갈 통로 (책 3-07의 `UserSecurityService`, 강사식 이름 Member). 빈으로 등록해 두면 023-03 폼 로그인이 자동으로 쓴다
- 결과: `user1`(비밀번호 1234)로 가입 후 조회하면 아이디 `user1`, 비밀번호는 `matches("1234", …)`가 true인 암호값, 권한 `ROLE_MEMBER`. `admin`은 `ROLE_MEMBER`, `ROLE_ADMIN` 둘 다. 없는 아이디는 `UsernameNotFoundException`. `gradlew test` 37개 통과
- 메모: 이 클래스는 비밀번호를 비교하지 않는다. 돌려준 암호값과 사용자가 입력한 비밀번호를 시큐리티가 `PasswordEncoder`(022-03)로 비교한다

### 023-02 로그인, 로그인 화면 GET /members/login
- 작업: `MemberController`에 `GET /members/login` → `member_login.html`. 템플릿은 `username`, `password` 입력칸과 `POST /members/login`으로 보내는 폼, 주소에 `?error`가 있으면 "아이디 또는 비밀번호를 확인해 주세요." 안내
- 이유: 로그인 화면 (책 3-07의 `/user/login`). 아이디·비밀번호 확인(POST)은 스프링 시큐리티가 처리하므로(023-03) 컨트롤러에는 화면 메서드만 둔다. 입력칸 이름 `username`, `password`는 시큐리티 기본값
- 결과: `GET /members/login` → 200, `action="/members/login"` 폼과 비밀번호 칸. 평소에는 안내 문구가 없고, `?error`로 열면 빨간 상자에 안내가 나온다. 아직 로그인 처리 설정이 없어 실제 로그인은 023-03부터. `gradlew test` 38개 통과
- 메모: 실패 안내는 아이디가 틀렸는지 비밀번호가 틀렸는지 나누지 않는다. 나누면 어떤 아이디가 가입돼 있는지 알려 주는 셈이 된다

### 023-03 로그인, 시큐리티 폼 로그인 설정
- 작업: `SecurityConfig`에 `.formLogin(form -> form.loginPage("/members/login").defaultSuccessUrl("/"))`. 테스트 `MemberLoginTests`(`SecurityMockMvcRequestBuilders.formLogin`)
- 이유: 아이디·비밀번호 확인을 스프링 시큐리티에 맡긴다 (책 3-07). `loginPage`를 주면 시큐리티 자동 로그인 화면 대신 023-02 화면을 쓰고, 같은 주소로 오는 POST를 시큐리티가 로그인 처리한다(023-01 `MemberSecurityService`로 회원을 찾고 022-03 `PasswordEncoder`로 비교)
- 결과: dev 서버에서 로그인 폼 토큰과 함께 `devuser`/`1234`를 보내면 302로 `/` 이동(로그인 성공), `devuser`/`wrong`이면 302로 `/members/login?error`(023-02 안내 문구가 나오는 주소). 테스트에서 성공 시 `authenticated().withUsername("user1")`, 실패 시 `unauthenticated()` 확인. `gradlew test` 40개 통과
- 메모: 로그인 성공 상태는 서버 세션에 저장되고 브라우저는 세션 쿠키(`JSESSIONID`)로 자신을 알린다

### 023-04 로그아웃, POST /members/logout 로그아웃 설정
- 작업: `SecurityConfig`에 `.logout(logout -> logout.logoutUrl("/members/logout").logoutSuccessUrl("/").invalidateHttpSession(true))`. 로그아웃 테스트 2개
- 이유: 로그아웃 (책 3-07). 책은 `GET /user/logout`으로 로그아웃하도록 설정을 바꾸지만, 시큐리티 기본대로 POST + CSRF 토큰으로만 받는다. GET이면 다른 사이트가 `<img src=".../logout">` 하나로 사용자를 몰래 로그아웃시킬 수 있다
- 결과: 테스트에서 실제로 로그인해 받은 세션으로 `POST /members/logout`을 보내면 로그아웃(`unauthenticated()`)되고 302로 `/` 이동. 같은 주소를 GET으로 열면 404(로그아웃 처리 대상이 아님). 화면의 로그아웃 버튼은 023-05에서 POST 폼으로 만든다. `gradlew test` 42개 통과
- 메모: `invalidateHttpSession(true)`는 서버 세션 자체를 없앤다. 세션에 담긴 로그인 정보도 함께 사라진다

### 023-05 로그인, 로그인 상태에 따라 바뀌는 메뉴
- 작업: `thymeleaf-extras-springsecurity6` 의존성 추가. `navbar.html` 오른쪽에 회원 메뉴를 두고 `sec:authorize="isAnonymous()"`이면 "로그인"(`/members/login`)·"회원가입", `sec:authorize="isAuthenticated()"`이면 `sec:authentication="name"`(아이디)과 "로그아웃" 버튼. 로그아웃 버튼은 `POST /members/logout`으로 보내는 `th:action` 폼
- 이유: 로그인 여부에 맞는 메뉴만 보여 준다 (책 3-07). 023-04에서 로그아웃을 POST로만 받게 했으므로 링크가 아니라 폼 버튼으로 만들었다(CSRF 토큰 자동 포함)
- 결과: dev 서버를 다시 켜서 한 세션으로 확인했다. 로그인 전 목록 화면 메뉴에는 로그인·회원가입 링크, `devuser`로 로그인한 뒤에는 로그아웃 버튼만(가입·로그인 링크 사라짐), 그 버튼으로 로그아웃하자 다시 로그인·회원가입 링크. 테스트에서 로그인 사용자 `user1`의 아이디가 메뉴에 `>user1</span>`으로 나오는 것도 확인. `gradlew test` 43개 통과
- 메모: 처음 테스트는 실패했다. navbar의 HTML 주석에 "로그아웃" 글자가 있어서 "로그아웃 글자가 없어야 한다"는 검사에 걸렸다. HTML 주석도 응답에 그대로 나가므로, 글자 대신 로그아웃 폼 주소(`action="/members/logout"`)로 검사하도록 바꿨다

### 024-01 글쓴이, 질문에 글쓴이 author 필드
- 작업: `Question`에 `@ManyToOne(fetch = FetchType.LAZY) private Member author;`. 저장 테스트 추가
- 이유: 누가 쓴 질문인지 저장한다 (책 3-08). 회원 한 명이 질문 여러 개를 쓰므로 `@ManyToOne`. 강사식으로 LAZY(004-03과 같은 이유: 질문만 볼 때는 회원을 읽지 않는다)
- 결과: `question` 테이블에 `author_id` 외래키 컬럼이 생긴다. 회원 `writer1`을 글쓴이로 넣어 저장하고 다시 읽으면 `getAuthor().getUsername()`이 `writer1`. dev DB의 기존 질문은 `author_id`가 비어 있다(null 허용). `gradlew test` 44개 통과
- 메모: dev 서버의 `ddl-auto: update`가 기존 `question` 테이블에 `author_id` 컬럼만 추가하고 데이터는 그대로 둔다

### 024-02 글쓴이, 답변에 글쓴이 author 필드
- 작업: `Answer`에 `@ManyToOne(fetch = FetchType.LAZY) private Member author;`. 024-01 테스트에 답변 글쓴이 확인 추가
- 이유: 누가 쓴 답변인지 저장한다 (책 3-08). 024-01 질문과 같은 관계·같은 LAZY
- 결과: `answer` 테이블에 `author_id` 외래키 컬럼이 생긴다. `addAnswer`로 만든 답변에 글쓴이 `writer1`을 넣고 저장한 뒤 다시 읽으면 글쓴이 아이디가 `writer1`. `gradlew test` 44개 통과

### 024-03 글쓴이, MemberService.getMember 아이디로 회원 찾기
- 작업: `MemberService.getMember(username)`이 `findByUsername`으로 회원을 찾고, 없으면 `DataNotFoundException("아이디 회원이 없습니다.")`(010-01, 404). 테스트 추가
- 이유: 로그인한 사용자 정보(`Principal`)에는 아이디 글자만 들어 있다. 질문·답변의 글쓴이(024-01, 024-02)로 저장하려면 `Member` 엔티티가 필요해서 아이디로 찾는다 (책 3-08의 `getUser`)
- 결과: `user1` 가입 후 `getMember("user1")`의 이메일이 `user1@test.com`. 없는 `nobody`는 `DataNotFoundException`과 메시지 "nobody 회원이 없습니다." `gradlew test` 45개 통과

### 024-04 글쓴이, 질문 저장 때 로그인 회원을 글쓴이로 기록
- 작업: `QuestionService.create(subject, content, author)`가 `question.setAuthor(author)`까지 저장. `QuestionController.create`가 `Principal principal`을 받아 `memberService.getMember(principal.getName())`(024-03)로 회원을 찾아 넘긴다. 테스트는 회원 `user1`을 만들고 `.with(user("user1"))`로 로그인한 상태에서 등록
- 이유: 024-01에서 만든 질문 글쓴이를 실제로 채운다 (책 3-08). `Principal`은 스프링 시큐리티가 컨트롤러에 넣어 주는 "지금 로그인한 사용자" 정보
- 결과: 로그인한 `user1`이 `POST /questions`로 등록하면 302로 목록 이동, 저장된 질문의 `getAuthor().getUsername()`이 `user1`. 서비스 테스트에서도 글쓴이 `writer1`이 저장됨. `gradlew test` 45개 통과
- 메모: 아직 로그인하지 않은 상태로 등록하면 `principal`이 없어 오류(500)가 난다. 024-06에서 로그인한 사용자만 등록할 수 있게 막는다

### 024-05 글쓴이, 답변 저장 때 로그인 회원을 글쓴이로 기록
- 작업: `AnswerService.create(question, content, author)`가 `addAnswer` 후 `answer.setAuthor(author)`. `AnswerController.create`가 `Principal`을 받아 `memberService.getMember(principal.getName())`로 글쓴이를 넘긴다. 답변 테스트도 `user1`로 로그인한 상태로 등록
- 이유: 024-02에서 만든 답변 글쓴이를 실제로 채운다 (책 3-08). 024-04 질문과 같은 방식
- 결과: 로그인한 `user1`이 `POST /questions/{id}/answers`로 등록하면 302로 상세 이동, 질문의 첫 답변 글쓴이가 `user1`. 서비스 테스트에서 글쓴이 `writer1` 확인. `gradlew test` 45개 통과

### 024-06 글쓴이, 로그인한 사용자만 질문·답변 등록 (@PreAuthorize)
- 작업: `SecurityConfig`에 `@EnableMethodSecurity`. 질문 등록 화면(`GET /questions/new`)과 등록(`POST /questions`), 답변 등록(`POST /questions/{id}/answers`)에 `@PreAuthorize("isAuthenticated()")`. 비로그인 테스트 2개 추가, 기존 폼 화면 테스트는 로그인 상태(`user("user1")`)로 변경
- 이유: 024-04, 024-05 뒤로는 로그인하지 않은 사용자가 등록하면 `principal`이 없어 500 오류가 난다. 글쓴이 없는 글도 막아야 한다 (책 3-08). 021-01에서 "처음엔 전부 열고 필요한 곳만 막는다"고 한 그 "필요한 곳"을 메서드 단위로 막는다
- 결과: 로그인 전에 `/questions/new`를 열거나 질문·답변을 등록하면 302로 `/members/login`(023-02 로그인 화면)으로 보내지고 아무것도 저장되지 않는다. 로그인한 사용자는 전처럼 등록된다. `gradlew test` 47개 통과
- 메모: 처음 테스트는 리다이렉트 주소를 `**/members/login` 패턴으로 비교해 실패했다. 실제 주소가 `/members/login` 그대로라 정확한 주소 비교로 고쳤다

### 024-07 글쓴이, 목록·상세 화면에 글쓴이 표시
- 작업: 목록에 `글쓴이` 칸(`${question.author?.username}`), 상세의 질문 카드와 답변 카드 오른쪽 아래에 글쓴이 아이디 배지(`badge bg-light text-dark`, 글쓴이가 없으면 `th:if`로 생략). 테스트의 기본 질문·답변에 글쓴이 `user1`을 넣고 화면 표시 확인
- 이유: 누가 쓴 글인지 화면에서 보여 준다 (책 3-08). 024 이전에 저장된 샘플 질문은 글쓴이가 없어서, 없을 때 오류가 나지 않게 처리했다
- 결과: dev 서버에서 `devuser`로 로그인해 "글쓴이 확인 질문"을 등록하자 목록 맨 위 줄의 글쓴이 칸에 `devuser`가 나왔다. 글쓴이 없는 옛 샘플 질문은 빈칸. 로그인 전에 `/questions/new`를 열면 `/members/login`으로 이동(024-06). 테스트에서 목록 `<td>user1</td>`, 상세 배지 `user1` 확인. `gradlew test` 47개 통과
- 메모: `?.`(안전 탐색)는 앞의 값이 null이면 오류 대신 null을 돌려준다. `question.author.username`으로 쓰면 글쓴이 없는 질문에서 오류가 난다

### 025-01 수정·삭제, _method 숨은 값으로 PUT·DELETE 받기 (HiddenHttpMethodFilter)
- 작업: `application.yaml`에 `spring.mvc.hiddenmethod.filter.enabled: true`. 필터 빈 등록 테스트
- 이유: HTML 폼은 `method`에 GET, POST만 쓸 수 있다. 강사식 REST는 수정을 `PUT /questions/{id}`, 삭제를 `DELETE /questions/{id}`로 받는다(책은 `POST /question/modify/{id}`, `GET /question/delete/{id}`). 이 필터를 켜면 폼에 `<input type="hidden" name="_method" value="PUT">`을 넣은 POST를 PUT으로 바꿔 컨트롤러에 넘긴다
- 결과: 앱에 `HiddenHttpMethodFilter` 빈이 등록된다(테스트로 확인). 실제로 쓰는 곳은 025-04(PUT), 025-06(DELETE). `gradlew test` 48개 통과
- 메모: 스프링 부트는 이 필터를 기본으로 꺼 둔다. 설정 한 줄로 켠다

### 025-02 수정·삭제, QuestionService.modify 질문 수정
- 작업: `QuestionService.modify(question, subject, content)`가 `setSubject`, `setContent`만 하고 `save`는 부르지 않는다. `@Transactional`. 테스트 추가
- 이유: 질문 수정 처리 (책 3-09). JPA는 트랜잭션 안에서 읽은 엔티티의 값이 바뀌면 끝날 때 알아서 `update`를 보낸다(변경 감지). 수정일시는 004-02 `@LastModifiedDate`가 자동으로 갱신한다(책은 `setModifyDate` 직접 호출)
- 결과: "수정 전" 질문을 "수정 후"/"바뀐 내용"으로 바꾸고 DB에 반영(`flush`)한 뒤 다시 읽으면 제목·내용이 바뀌어 있고, `modifyDate`가 `createDate`보다 늦다. `gradlew test` 49개 통과
- 메모: 테스트에서 생성 직후 바로 수정하면 두 시각이 같을 수 있어 5ms 기다렸다
