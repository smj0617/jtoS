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
- 작업: `domain.question.question.service.QuestionService` 추가(`@Service`, `@RequiredArgsConstructor` + `final QuestionRepository`). 목록 조회 메서드 `getList()`
- 이유: 컨트롤러(요청·응답), 서비스(업무 처리), 리포지터리(DB 접근)로 역할을 나누려고 (책 2-09). 생성자 주입은 강사식
- 결과: 컴파일과 기존 테스트 8개 통과. 아직 컨트롤러는 리포지터리를 직접 쓴다(009-02에서 교체)

### 009-02 서비스, 컨트롤러가 리포지터리 대신 서비스 사용
- 작업: `QuestionController`가 `QuestionRepository` 대신 `QuestionService`를 주입받고, `list()`에서 `getList()`를 호출
- 이유: 컨트롤러는 DB를 직접 다루지 않고 서비스에 처리를 맡긴다 (책 2-09)
- 결과: 테스트 통과. 목록 화면 결과는 그대로

### 009-03 서비스, QuestionService 테스트 추가
- 작업: `QuestionServiceTests`에서 질문 2개를 저장한 뒤 `getList()`가 두 제목을 순서대로 돌려주는지 확인
- 이유: 컨트롤러 테스트와 서비스 테스트를 나눠 두면 실패했을 때 화면 문제인지 처리 문제인지 바로 알 수 있다
- 결과: 테스트 9개 통과

### 010-01 상세, 데이터가 없을 때 쓰는 DataNotFoundException 추가
- 작업: `global.exception.DataNotFoundException`(`RuntimeException` 상속, `@ResponseStatus(NOT_FOUND)`, 메시지를 생성자로 받음)
- 이유: 없는 데이터를 요청하면 서버 오류(500)가 아니라 "없음"(404)으로 응답해야 한다 (책 2-10). 책의 고정 문구 대신 무엇이 없는지 메시지를 받는다(강사식)
- 결과: 테스트 통과. 실제 사용은 010-02

### 010-02 상세, QuestionService.getQuestion 단건 조회
- 작업: `getQuestion(id)`가 `findById(id).orElseThrow(...)`로 질문을 찾고, 없으면 `DataNotFoundException("N번 질문이 없습니다.")`
- 이유: 상세 화면에 쓸 질문 하나 조회 (책 2-10). `Optional`을 꺼낼 때 `get()` 대신 `orElseThrow`로 없는 경우를 명확히 처리
- 결과: 테스트 10개 통과 (성공 조회, 없는 id 999999 → 예외와 메시지)

### 010-03 상세, 질문 상세 화면
- 작업: `GET /question/detail/{id}`가 `@PathVariable`로 id를 받아 `getQuestion(id)`를 `question_detail.html`로 그린다. 템플릿은 제목과 내용
- 이유: 질문 하나를 보는 화면 (책 2-10)
- 결과: 테스트 11개 통과. 상세 200 + 내용 포함, 없는 id 999999는 404 (010-01 예외의 `@ResponseStatus` 동작 확인)

### 010-04 상세, 목록의 제목을 상세 화면 링크로
- 작업: `question_list.html`의 제목을 `th:href="@{|/question/detail/${question.id}|}"` 링크로 변경
- 이유: 목록에서 제목을 눌러 상세로 이동 (책 2-10)
- 결과: 테스트 통과. 목록 HTML에 `href="/question/detail/{id}"` 포함
- 메모: `@{}`는 앱 기준 주소를 만들고, `|...|`는 글자와 `${}` 값을 이어 붙이는 문법이다

### 011-01 URL 프리픽스, 컨트롤러에 공통 주소 /question 적용
- 작업: `QuestionController` 클래스에 `@RequestMapping("/question")`, 메서드는 `/list`, `/detail/{id}`
- 이유: 주소 앞부분을 한곳에서 관리 (책 2-11)
- 결과: 실제 주소는 바뀌지 않아 기존 테스트 11개 그대로 통과

### 011-02 URL 프리픽스, REST 주소 /questions, /questions/{id}로 변경
- 작업: 목록 `GET /questions`, 상세 `GET /questions/{id}`. 목록 링크, 루트 리다이렉트, 테스트 주소 변경
- 이유: 강사식 REST 규칙. 자원은 복수형 명사로 쓰고 `list`, `detail` 같은 동작은 주소에 넣지 않는다. 동작은 HTTP 메서드(GET 조회, POST 생성, PUT 수정, DELETE 삭제)로 구분한다
- 결과: 테스트 11개 통과
- 메모: 책은 `/question/list`, `/question/detail/{id}`를 계속 쓴다. 이후 단계의 주소는 모두 REST 규칙으로 바꿔 적용한다

### 012-01 답변, AnswerService.create 답변 저장
- 작업: `domain.question.answer.service.AnswerService.create(question, content)`가 `question.addAnswer(content)`로 답변을 만들고 저장. `@Transactional`
- 이유: 답변 저장 처리 (책 2-12). 책처럼 `setQuestion`, `setCreateDate`를 직접 하지 않고 004-03의 `addAnswer`와 004-02 Auditing을 쓴다
- 결과: 테스트 12개 통과 (id·생성일시가 채워지고, 답변↔질문 양쪽 관계가 맞음)

### 012-02 답변, AnswerController 답변 등록 요청 처리
- 작업: `POST /questions/{questionId}/answers`가 `content`를 받아 저장하고 `redirect:/questions/{questionId}`
- 이유: 답변 폼이 보낼 요청 처리 (책 2-12). 답변은 질문에 속한 자원이라 질문 주소 아래에 둔다(강사식, 책은 `/answer/create/{id}`)
- 결과: 테스트 13개 통과 (302 → 상세 주소, 질문의 답변 목록에 내용 추가)
- 메모: 저장 후 화면을 바로 그리지 않고 리다이렉트하는 것을 PRG(Post-Redirect-Get)라 한다. 새로고침해도 POST가 다시 가지 않는다

### 012-03 답변, 상세 화면에 답변 입력 폼
- 작업: `question_detail.html`에 `POST /questions/{id}/answers`로 보내는 폼(`textarea name="content"`, 답변등록 버튼)
- 이유: 상세 화면에서 답변 입력 (책 2-12). 입력칸의 `name`이 컨트롤러 `@RequestParam` 이름과 같아야 값이 들어간다
- 결과: 테스트 13개 통과 (상세 HTML에 `action="/questions/{id}/answers"`)

### 012-04 답변, 상세 화면에 답변 개수와 목록 표시
- 작업: `question_detail.html`에 `${#lists.size(question.answerList)}개의 답변이 있습니다.`와 `th:each` 답변 목록
- 이유: 등록된 답변 보기 (책 2-12)
- 결과: 테스트 13개 통과 (답변 1개 → "1개의 답변이 있습니다.", 답변 내용 표시)
- 메모: `answerList`는 LAZY라 템플릿에서 처음 쓸 때 조회된다. 화면을 그리는 동안에도 DB 연결이 열려 있는 것은 `open-in-view`(003-03 경고) 기본값 덕분이다

### 013-01 웹 디자인, 공통 CSS 파일 static/resource/global.css
- 작업: `src/main/resources/static/resource/global.css`(textarea 가로 100%, 제출 버튼 위 여백) 추가. `/resource/global.css` 제공 테스트
- 이유: 스타일을 한 파일에 모은다 (책 2-13의 `style.css`). 정적 파일은 `static/resource`에 둔다(강사식)
- 결과: 테스트 14개 통과
- 메모: `static` 폴더의 파일은 컨트롤러 없이 그 경로 그대로 주소가 된다

### 013-02 웹 디자인, 목록·상세 템플릿에 공통 CSS 연결
- 작업: 두 템플릿의 `<head>`에 `<link rel="stylesheet" th:href="@{/resource/global.css}">`
- 이유: 013-01 스타일을 화면에 적용 (책 2-13)
- 결과: 테스트 14개 통과 (목록 HTML에 `href="/resource/global.css"`)
- 메모: 같은 `<head>`를 템플릿마다 반복하고 있다. 015 레이아웃에서 한곳으로 모은다

### 014-01 부트스트랩, 부트스트랩 CSS를 cdnjs CDN으로 연결
- 작업: 두 템플릿에 `https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css` 링크. `global.css`보다 먼저 불러온다
- 이유: 부트스트랩 클래스를 쓰기 위해 (책 2-14). 책은 파일을 내려받아 `static`에 넣지만 강사식은 CDN, 버전은 5.3.3으로 고정
- 결과: 테스트 14개 통과
- 메모: CSS는 나중에 불러온 것이 이긴다. 우리 스타일이 부트스트랩을 덮어쓸 수 있게 순서를 정했다

### 014-02 부트스트랩, Pretendard 한글 폰트 적용
- 작업: 두 템플릿에 Pretendard v1.3.9 CDN 링크, `global.css`의 `body`에 `font-family: "Pretendard", ... sans-serif`
- 이유: 읽기 좋은 한글 웹폰트 (강사식)
- 결과: 테스트 14개 통과
- 메모: 폰트 파일을 불러오는 것(link)과 실제로 쓰는 것(font-family)은 별개다. 뒤쪽 글꼴들은 폰트를 못 받았을 때의 대체 글꼴

### 014-03 부트스트랩, 목록 화면에 부트스트랩 클래스 적용
- 작업: 목록을 `container my-3`으로 감싸고, 표에 `table`, 머리줄에 `table-dark`
- 이유: 목록 화면 모양 정리 (책 2-14)
- 결과: 테스트 14개 통과

### 014-04 부트스트랩, 상세 화면에 부트스트랩 카드 적용
- 작업: 상세 화면을 `container my-3`으로 감싸고, 질문 내용과 답변을 각각 `card`로, 입력칸 `form-control`, 버튼 `btn btn-primary`. 내용 줄바꿈은 `white-space: pre-line`
- 이유: 상세 화면 모양 정리 (책 2-14)
- 결과: 테스트 14개 통과
- 메모: 답변 표시는 `<ul><li>`에서 카드로 바뀌었다. 012-04 테스트는 글자만 확인하므로 그대로 통과한다

### 015-01 레이아웃, 타임리프 레이아웃 다이얼렉트 의존성 추가
- 작업: `thymeleaf-layout-dialect` 의존성 추가 (버전은 Spring Boot가 관리)
- 이유: 화면마다 반복되는 `<head>`와 바깥 틀을 하나로 모으기 위해 (강사식, 책 2-15는 `th:replace`)
- 결과: 테스트 14개 통과

### 015-02 레이아웃, 공통 틀 layout.html 추가
- 작업: `templates/layout.html`에 공통 `<head>`(부트스트랩, Pretendard, `global.css`)와 내용 자리 `<div layout:fragment="content">`
- 이유: 반복되는 `<head>`를 한 파일에 모은다 (강사식)
- 결과: 테스트 14개 통과. 화면 적용은 015-03, 015-04

### 015-03 레이아웃, 목록 화면이 layout.html 사용
- 작업: `question_list.html`에 `layout:decorate="~{layout}"`, 내용 `div`에 `layout:fragment="content"`. 링크 3줄 삭제
- 이유: 공통 부분을 레이아웃에 맡긴다 (강사식)
- 결과: 테스트 14개 통과. 013-02, 014-01 테스트(목록 HTML에 CSS 링크 존재)가 그대로 통과 → 레이아웃이 합쳐져 나온다

### 015-04 레이아웃, 상세 화면이 layout.html 사용
- 작업: `question_detail.html`에 `layout:decorate="~{layout}"`, 바깥 `div`에 `layout:fragment="content"`. 링크 3줄 삭제
- 이유: 이제 공통 `<head>`는 `layout.html` 한 곳에서만 관리한다
- 결과: 테스트 14개 통과 (상세 HTML에도 레이아웃의 부트스트랩 링크 포함)

### 015-05 레이아웃, 화면 제목 패턴 layout:title-pattern
- 작업: `layout.html`의 `<title>`에 `layout:title-pattern="$CONTENT_TITLE | $LAYOUT_TITLE"`
- 이유: 탭 제목을 "화면 이름 | SBB"로 통일 (강사식)
- 결과: 테스트 14개 통과 (목록 `<title>질문 목록 | SBB</title>`)

### 015-06 레이아웃, 공통 자바스크립트 global.js
- 작업: `static/resource/global.js`(`onReady` 도우미) 추가, `layout.html`에서 `<script defer>`로 불러옴
- 이유: 여러 화면이 함께 쓸 JS를 한 곳에 모은다 (강사식). 016, 025에서 함수를 더한다
- 결과: 테스트 15개 통과

### 016-01 질문 등록, QuestionService.create 질문 저장
- 작업: `QuestionService.create(subject, content)`(`@Transactional`) 추가와 테스트
- 이유: 질문 등록 처리 (책 2-16). 생성일시는 004-02 Auditing이 채운다
- 결과: 테스트 16개 통과

### 016-02 질문 등록, 등록 폼 화면 GET /questions/new
- 작업: `GET /questions/new` → `question_form.html`(제목, 내용, `POST /questions`로 보내는 폼)
- 이유: 질문 입력 화면 (책 2-16). REST 규칙으로 `/questions/new` (책은 `/question/create`)
- 결과: 테스트 17개 통과

### 016-03 질문 등록, 저장 요청 POST /questions
- 작업: `POST /questions`가 `subject`, `content`를 받아 저장하고 `redirect:/questions`
- 이유: 등록 폼의 질문 저장 (책 2-16). 같은 자원 주소에 POST = 생성(REST)
- 결과: 테스트 18개 통과

### 016-04 질문 등록, 목록에 질문 등록하기 버튼
- 작업: 목록 표 아래 `/questions/new`로 가는 `btn btn-primary` 링크
- 이유: 목록 → 등록 화면 이동 (책 2-16)
- 결과: 테스트 18개 통과

### 016-05 질문 등록, record 폼 QuestionForm과 서버 검증
- 작업: `spring-boot-starter-validation` 추가. `QuestionForm` record(`@NotBlank`, `@Size(max = 200)`)를 `@Valid`로 받고 `BindingResult`에 오류가 있으면 폼을 다시 보여 준다
- 이유: 빈 값과 너무 긴 제목을 서버에서 막는다 (책 2-16). 강사식으로 폼은 record
- 결과: 테스트 19개 통과 (공백 제목 → 200 + `question_form`, 저장 개수 변화 없음)

### 016-06 질문 등록, th:object·th:field와 필드별 오류 메시지
- 작업: 등록 폼에 `th:object="${questionForm}"`, 입력칸 `th:field`, `th:errorclass="is-invalid"`, 칸 아래 `th:errors`. GET 폼 화면에 빈 `QuestionForm` 제공
- 이유: 틀린 칸을 바로 아래에 표시하고 입력값을 유지 (책 2-16은 오류를 폼 위에 한꺼번에 표시, 강사식은 칸마다)
- 결과: 테스트 19개 통과 (메시지 "제목은 필수항목입니다.", `is-invalid`, 입력한 내용 유지)

### 016-07 질문 등록, 브라우저 검증 required·maxlength
- 작업: 제목에 `required maxlength="200"`, 내용에 `required`
- 이유: 서버에 가기 전에 브라우저에서 먼저 막는다. 서버 검증과 같은 규칙으로 양쪽에서 검증(강사식)
- 결과: 테스트 19개 통과
- 메모: 브라우저 검증은 개발자 도구나 직접 요청으로 우회할 수 있다. 그래서 서버 검증(016-05)이 진짜 방어선이다

### 016-08 질문 등록, 첫 오류 입력칸에 자동 focus
- 작업: `global.js`에 `onReady(() => document.querySelector(".is-invalid")?.focus())` 동작 추가
- 이유: 검증 실패 후 고칠 칸으로 바로 커서 이동 (강사식). 공통 JS라 모든 폼에 적용
- 결과: 테스트 19개 통과

### 016-09 질문 등록, 답변 AnswerForm과 서버 검증
- 작업: `AnswerForm` record(`@NotBlank content`), `AnswerController`가 `@Valid AnswerForm` + `BindingResult`로 받고 오류면 상세 화면을 다시 그림
- 이유: 빈 답변 방지 (책 2-16). 질문과 같은 방식
- 결과: 테스트 20개 통과

### 016-10 질문 등록, 답변 폼에 th:object·오류 표시·required
- 작업: 상세 화면 답변 폼에 `th:object="${answerForm}"`, `th:field`, `is-invalid`, `th:errors`, `required`. 상세 GET이 빈 `AnswerForm` 제공
- 이유: 답변 폼도 질문 폼과 같은 방식으로 오류 표시 (책 2-16)
- 결과: 테스트 20개 통과

### 017-01 내비게이션 바, navbar.html 프래그먼트 파일
- 작업: `templates/navbar.html`에 `th:fragment="navbar"` 내비게이션 바(로고, 햄버거 버튼, 질문 목록 메뉴)
- 이유: 공통 메뉴 (책 3-01). 레이아웃과 분리된 조각 파일로 관리(강사식)
- 결과: 테스트 20개 통과. 연결은 017-02

### 017-02 내비게이션 바, 레이아웃에 navbar 붙이기
- 작업: `layout.html`의 `<body>` 맨 위에 `<nav th:replace="~{navbar :: navbar}">`
- 이유: 레이아웃에 한 번 붙이면 모든 화면에 메뉴가 나온다 (책 3-01)
- 결과: 테스트 20개 통과

### 017-03 내비게이션 바, 부트스트랩 JS로 햄버거 메뉴 동작
- 작업: `layout.html`에 `bootstrap.bundle.min.js`(5.3.3, cdnjs, `defer`)
- 이유: CSS는 모양만, 펼치기 같은 동작은 JS가 한다 (책 3-01)
- 결과: 테스트 20개 통과
