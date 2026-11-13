package com.mysite.sbb.domain.question.answer.controller;

import com.mysite.sbb.domain.member.member.service.MemberService;
import com.mysite.sbb.domain.question.answer.entity.Answer;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
// 021-01: 시큐리티가 켜지면 POST 에 CSRF 토큰이 필요하다. 테스트 요청에는 csrf() 로 토큰을 붙인다
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

// 012-02: 새 파일. 답변 등록 요청 테스트
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AnswerControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private QuestionRepository questionRepository;

    // 024-05: 답변 등록에 쓸 로그인 회원
    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService.join("user1", "user1@test.com", "1234");
    }

    @Test
    @DisplayName("POST /questions/{id}/answers 는 답변을 저장하고 상세로 리다이렉트한다")
    void create() throws Exception {
        Question q = new Question();
        q.setSubject("질문");
        q.setContent("내용");
        questionRepository.save(q);

        String location = mvc.perform(post("/questions/%d/answers".formatted(q.getId())).with(csrf()).with(user("user1"))
                        .param("content", "답변입니다."))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().getRedirectedUrl();

        assertThat(q.getAnswerList()).extracting("content").containsExactly("답변입니다.");
        // 027-01: 등록 후 새 답변 위치(#answer_id)로 이동하고, 상세 화면에 그 id 의 카드가 있다
        Integer newId = q.getAnswerList().get(0).getId();
        assertThat(location).isEqualTo("/questions/%d#answer_%d".formatted(q.getId(), newId));
        mvc.perform(get("/questions/" + q.getId())).andExpect(content().string(containsString("id=\"answer_" + newId + "\"")));
        // 024-05: 로그인한 user1 이 답변 글쓴이로 저장된다
        assertThat(q.getAnswerList().get(0).getAuthor().getUsername()).isEqualTo("user1");
    }

    // 025-09: 답변 수정 화면과 수정 처리
    @Test
    @DisplayName("답변 글쓴이는 수정 화면을 열고 POST + _method=put 으로 수정한다. 다른 회원은 403")
    void modify() throws Exception {
        Question q = new Question();
        q.setSubject("질문");
        q.setContent("내용");
        questionRepository.save(q);
        Answer a = q.addAnswer("처음 답변");
        a.setAuthor(memberService.getMember("user1"));
        questionRepository.flush();

        mvc.perform(get("/questions/%d/answers/%d/edit".formatted(q.getId(), a.getId())).with(user("user1")))
                .andExpect(status().isOk())
                .andExpect(view().name("answer_modify"))
                .andExpect(content().string(containsString(">처음 답변</textarea>")));

        mvc.perform(post("/questions/%d/answers/%d".formatted(q.getId(), a.getId())).with(csrf()).with(user("user1"))
                        .param("_method", "put").param("content", "고친 답변"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/questions/" + q.getId()));
        assertThat(a.getContent()).isEqualTo("고친 답변");

        memberService.join("other", "other@test.com", "1234");
        mvc.perform(get("/questions/%d/answers/%d/edit".formatted(q.getId(), a.getId())).with(user("other")))
                .andExpect(status().isForbidden());
    }

    // 026-05: 답변 추천
    @Test
    @DisplayName("로그인 회원이 답변 votes 에 POST 하면 답변 추천 수가 오르고 상세에 1이 보인다")
    void vote() throws Exception {
        Question q = new Question();
        q.setSubject("질문");
        q.setContent("내용");
        questionRepository.save(q);
        Answer a = q.addAnswer("추천할 답변");
        questionRepository.flush();

        mvc.perform(post("/questions/%d/answers/%d/votes".formatted(q.getId(), a.getId())).with(csrf()).with(user("user1")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/questions/" + q.getId()));
        assertThat(a.getVoter()).extracting("username").containsExactly("user1");

        mvc.perform(get("/questions/" + q.getId()))
                .andExpect(content().string(containsString("/answers/%d/votes\" method=\"POST\"".formatted(a.getId()))));
    }

    // 025-10: 답변 삭제
    @Test
    @DisplayName("다른 회원의 답변 DELETE 는 403, 글쓴이는 삭제하고 상세로 이동. 상세에는 삭제 링크가 있다")
    void delete() throws Exception {
        Question q = new Question();
        q.setSubject("질문");
        q.setContent("내용");
        questionRepository.save(q);
        Answer a = q.addAnswer("지울 답변");
        a.setAuthor(memberService.getMember("user1"));
        questionRepository.flush();

        mvc.perform(get("/questions/" + q.getId()).with(user("user1")))
                .andExpect(content().string(containsString("href=\"/questions/%d/answers/%d\" method=\"DELETE\"".formatted(q.getId(), a.getId()))));

        memberService.join("other", "other@test.com", "1234");
        mvc.perform(MockMvcRequestBuilders.delete("/questions/%d/answers/%d".formatted(q.getId(), a.getId())).with(csrf()).with(user("other")))
                .andExpect(status().isForbidden());
        assertThat(q.getAnswerList()).hasSize(1);

        mvc.perform(MockMvcRequestBuilders.delete("/questions/%d/answers/%d".formatted(q.getId(), a.getId())).with(csrf()).with(user("user1")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/questions/" + q.getId()));
        assertThat(q.getAnswerList()).isEmpty();
    }

    // 024-06: 로그인하지 않으면 답변 등록이 로그인 화면으로 보내진다
    @Test
    @DisplayName("로그인 전 답변 등록은 로그인 화면으로 리다이렉트되고 저장되지 않는다")
    void createRequiresLogin() throws Exception {
        Question q = new Question();
        q.setSubject("질문");
        q.setContent("내용");
        questionRepository.save(q);

        mvc.perform(post("/questions/%d/answers".formatted(q.getId())).with(csrf())
                        .param("content", "비로그인 답변"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/members/login"));

        assertThat(q.getAnswerList()).isEmpty();
    }

    // 016-09: 답변 서버 검증 실패
    @Test
    @DisplayName("답변 내용이 비어 있으면 저장하지 않고 상세 화면을 다시 보여 준다")
    void createInvalid() throws Exception {
        Question q = new Question();
        q.setSubject("질문");
        q.setContent("내용");
        questionRepository.save(q);

        mvc.perform(post("/questions/%d/answers".formatted(q.getId())).with(csrf()).with(user("user1"))
                        .param("content", " "))
                .andExpect(status().isOk())
                .andExpect(view().name("question_detail"))
                // 016-10: 상세 화면 답변 폼에 오류 메시지와 빨간 테두리가 보이는지 확인
                .andExpect(content().string(containsString("내용은 필수항목입니다.")))
                .andExpect(content().string(containsString("is-invalid")));

        assertThat(q.getAnswerList()).isEmpty();
    }
}
