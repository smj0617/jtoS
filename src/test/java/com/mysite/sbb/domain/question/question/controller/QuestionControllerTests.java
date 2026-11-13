package com.mysite.sbb.domain.question.question.controller;

import com.mysite.sbb.domain.member.member.entity.Member;
import com.mysite.sbb.domain.member.member.service.MemberService;
import com.mysite.sbb.domain.question.question.entity.Question;
import com.mysite.sbb.domain.question.question.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
// 021-01: 시큐리티가 켜지면 POST 에 CSRF 토큰이 필요하다. 테스트 요청에는 csrf() 로 토큰을 붙인다
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

// 007-03: 새 파일. 서버를 띄우지 않고 가짜 요청(MockMvc)으로 컨트롤러와 템플릿 결과를 확인한다
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class QuestionControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private QuestionRepository questionRepository;

    private Question q1;

    // 024-04: 질문 등록에 쓸 로그인 회원
    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        // 024-07: setUp 질문·답변의 글쓴이로도 쓴다
        Member user1 = memberService.join("user1", "user1@test.com", "1234");
        q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setAuthor(user1);
        q1.addAnswer("스프링부트 게시판입니다.").setAuthor(user1);
        questionRepository.save(q1);
    }

    // 008-01: 루트 URL 리다이렉트 확인
    // 011-02: 테스트의 주소도 모두 /questions, /questions/{id} 로 변경
    @Test
    @DisplayName("/ 로 들어오면 질문 목록으로 리다이렉트된다")
    void root() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/questions"));
    }

    @Test
    @DisplayName("질문 목록 화면에 저장된 질문 제목이 보인다")
    void list() throws Exception {
        mvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(view().name("question_list"))
                .andExpect(content().string(containsString("sbb가 무엇인가요?")))
                // 020-01: setUp 의 질문(답변 1개) 제목 옆에 답변 개수 1
                .andExpect(content().string(containsString("<span class=\"text-danger small ms-2\">1</span>")))
                // 017-03: 부트스트랩 JS 를 불러오는지 확인
                .andExpect(content().string(containsString("bootstrap/5.3.3/js/bootstrap.bundle.min.js")))
                // 017-02: 레이아웃을 통해 내비게이션 바가 붙었는지 확인
                .andExpect(content().string(containsString("class=\"navbar-brand\"")))
                // 016-04: 목록에 질문 등록 버튼이 있는지 확인
                .andExpect(content().string(containsString("href=\"/questions/new\"")))
                // 015-06: 레이아웃이 공통 자바스크립트를 불러오는지 확인
                .andExpect(content().string(containsString("src=\"/resource/global.js\"")))
                // 015-05: 화면 제목과 사이트 제목이 패턴대로 합쳐지는지 확인
                .andExpect(content().string(containsString("<title>질문 목록 | SBB</title>")))
                // 024-07: 목록에 글쓴이 칸과 아이디
                .andExpect(content().string(containsString("<th>글쓴이</th>")))
                .andExpect(content().string(containsString("<td>user1</td>")))
                // 014-03: 목록 표에 부트스트랩 클래스가 붙었는지 확인
                .andExpect(content().string(containsString("<table class=\"table\">")))
                // 014-01: 부트스트랩 CDN 을 불러오는지 확인
                .andExpect(content().string(containsString("bootstrap/5.3.3/css/bootstrap.min.css")))
                // 013-02: 공통 CSS 를 불러오는지 확인
                .andExpect(content().string(containsString("href=\"/resource/global.css\"")))
                // 010-04: 목록의 제목이 상세 링크인지 확인
                .andExpect(content().string(containsString("href=\"/questions/" + q1.getId() + "\"")));
    }

    // 018-03: ?page 값으로 페이지 고르기
    @Test
    @DisplayName("?page=2 는 두 번째 페이지(11번째 이후 질문)를 보여 준다")
    void listPage() throws Exception {
        // setUp 의 q1 이 가장 먼저 저장됐으므로, 11개를 더 저장하면 최신순에서 12번째 → 2페이지로 밀려난다
        for (int i = 1; i <= 11; i++) {
            Question q = new Question();
            q.setSubject("페이지질문" + i);
            q.setContent("내용");
            questionRepository.save(q);
        }

        mvc.perform(get("/questions").param("page", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("sbb가 무엇인가요?")))
                // 019-01: 12개 중 2페이지 = 번호 2, 1 (가장 오래된 질문이 1번)
                .andExpect(content().string(containsString("<td>2</td>")))
                .andExpect(content().string(containsString("<td>1</td>")));

        mvc.perform(get("/questions").param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("sbb가 무엇인가요?"))))
                // 019-01: 1페이지 맨 위(가장 최신)가 12번
                .andExpect(content().string(containsString("<td>12</td>")))
                // 018-04: 페이지 버튼 확인 (2페이지 링크, 현재 페이지 active, 1페이지라 이전은 disabled)
                .andExpect(content().string(containsString("href=\"/questions?page=2\"")))
                .andExpect(content().string(containsString("page-item active")))
                .andExpect(content().string(containsString("page-item disabled")));
    }

    // 016-02: 등록 폼 화면
    @Test
    @DisplayName("GET /questions/new 는 질문 등록 폼을 보여 준다")
    void showCreate() throws Exception {
        mvc.perform(get("/questions/new").with(user("user1")))
                .andExpect(status().isOk())
                .andExpect(view().name("question_form"))
                .andExpect(content().string(containsString("<title>질문 등록 | SBB</title>")))
                .andExpect(content().string(containsString("action=\"/questions\"")))
                // 016-07: 브라우저 검증 속성 확인
                .andExpect(content().string(containsString("maxlength=\"200\"")))
                .andExpect(content().string(containsString("required")));
    }

    // 016-03: 질문 저장
    @Test
    @DisplayName("POST /questions 는 질문을 저장하고 목록으로 리다이렉트한다")
    void create() throws Exception {
        mvc.perform(post("/questions").with(csrf()).with(user("user1"))
                        .param("subject", "새 질문")
                        .param("content", "새 내용"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/questions"));

        assertThat(questionRepository.findBySubject("새 질문")).isPresent();
        // 024-04: 로그인한 user1 이 글쓴이로 저장된다
        assertThat(questionRepository.findBySubject("새 질문").get().getAuthor().getUsername()).isEqualTo("user1");
    }

    // 021-03: th:action 을 쓴 폼에는 타임리프가 CSRF 토큰 숨은 입력칸을 자동으로 넣는다
    @Test
    @DisplayName("질문 등록 폼과 답변 폼에 _csrf 숨은 입력칸이 들어 있다")
    void formsHaveCsrfToken() throws Exception {
        mvc.perform(get("/questions/new").with(user("user1")))
                .andExpect(content().string(containsString("type=\"hidden\" name=\"_csrf\"")));
        mvc.perform(get("/questions/" + q1.getId()))
                .andExpect(content().string(containsString("type=\"hidden\" name=\"_csrf\"")));
    }

    // 021-02: 응답 헤더가 같은 사이트 프레임만 허용하는지 확인 (H2 콘솔 프레임 화면용)
    @Test
    @DisplayName("응답의 X-Frame-Options 는 SAMEORIGIN 이다")
    void frameOptions() throws Exception {
        mvc.perform(get("/questions"))
                .andExpect(header().string("X-Frame-Options", "SAMEORIGIN"));
    }

    // 025-03: 수정 화면은 글쓴이 본인만
    @Test
    @DisplayName("글쓴이는 수정 화면에서 기존 제목·내용을 보고, 다른 사람은 403")
    void showModify() throws Exception {
        mvc.perform(get("/questions/%d/edit".formatted(q1.getId())).with(user("user1")))
                .andExpect(status().isOk())
                .andExpect(view().name("question_modify"))
                .andExpect(content().string(containsString("value=\"sbb가 무엇인가요?\"")))
                .andExpect(content().string(containsString("name=\"_method\" value=\"put\"")))
                .andExpect(content().string(containsString("action=\"/questions/" + q1.getId() + "\"")));

        memberService.join("other", "other@test.com", "1234");
        mvc.perform(get("/questions/%d/edit".formatted(q1.getId())).with(user("other")))
                .andExpect(status().isForbidden());
    }

    // 024-06: 로그인하지 않으면 등록 화면·등록 요청이 로그인 화면으로 보내진다
    @Test
    @DisplayName("로그인 전에는 질문 등록 화면과 등록 요청이 로그인 화면으로 리다이렉트되고 저장되지 않는다")
    void createRequiresLogin() throws Exception {
        mvc.perform(get("/questions/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/members/login"));

        mvc.perform(post("/questions").with(csrf())
                        .param("subject", "비로그인 질문").param("content", "내용"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/members/login"));

        assertThat(questionRepository.findBySubject("비로그인 질문")).isEmpty();
    }

    // 021-01: CSRF 토큰 없이 보낸 POST 는 시큐리티가 막는다
    @Test
    @DisplayName("CSRF 토큰이 없는 POST /questions 는 403 으로 거부된다")
    void createWithoutCsrf() throws Exception {
        mvc.perform(post("/questions")
                        .param("subject", "새 질문")
                        .param("content", "새 내용"))
                .andExpect(status().isForbidden());

        assertThat(questionRepository.findBySubject("새 질문")).isEmpty();
    }

    // 016-05: 서버 검증 실패
    @Test
    @DisplayName("제목이 비어 있으면 저장하지 않고 등록 폼을 다시 보여 준다")
    void createInvalid() throws Exception {
        long before = questionRepository.count();

        mvc.perform(post("/questions").with(csrf()).with(user("user1"))
                        .param("subject", " ")
                        .param("content", "내용"))
                .andExpect(status().isOk())
                .andExpect(view().name("question_form"))
                // 016-06: 오류 메시지와 빨간 테두리, 입력했던 내용이 남아 있는지 확인
                .andExpect(content().string(containsString("제목은 필수항목입니다.")))
                .andExpect(content().string(containsString("is-invalid")))
                .andExpect(content().string(containsString(">내용</textarea>")));

        assertThat(questionRepository.count()).isEqualTo(before);
    }

    // 010-03: 상세 화면과 없는 질문의 404
    @Test
    @DisplayName("질문 상세 화면에 제목과 내용이 보이고, 없는 질문은 404")
    void detail() throws Exception {
        mvc.perform(get("/questions/" + q1.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("question_detail"))
                .andExpect(content().string(containsString("sbb에 대해서 알고 싶습니다.")))
                // 015-04: 상세 화면도 레이아웃을 통해 부트스트랩 링크를 받는지 확인
                .andExpect(content().string(containsString("bootstrap/5.3.3/css/bootstrap.min.css")))
                // 024-07: 상세의 질문·답변 카드에 글쓴이 배지 (질문 1 + 답변 1 = 2개)
                .andExpect(content().string(containsString("<span class=\"badge bg-light text-dark p-2\">user1</span>")))
                // 014-04: 질문·답변이 부트스트랩 카드로 그려지는지 확인
                .andExpect(content().string(containsString("class=\"card my-3\"")))
                .andExpect(content().string(containsString("class=\"btn btn-primary my-2\"")))
                // 012-04: 답변 개수와 답변 내용이 보이는지 확인 (setUp 에서 답변 1개 저장)
                .andExpect(content().string(containsString("1개의 답변이 있습니다.")))
                .andExpect(content().string(containsString("스프링부트 게시판입니다.")))
                // 012-03: 답변 폼이 답변 등록 주소로 POST 하는지 확인
                .andExpect(content().string(containsString("action=\"/questions/" + q1.getId() + "/answers\"")));

        mvc.perform(get("/questions/999999"))
                .andExpect(status().isNotFound());
    }
}
