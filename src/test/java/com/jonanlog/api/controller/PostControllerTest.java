package com.jonanlog.api.controller;

import com.jonanlog.api.domain.Post;
import com.jonanlog.api.repository.PostRepository;
import com.jonanlog.api.request.PostCreate;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        // 각각의 메소드들이 실행되기 전에 clean() 메소드를 수행한다.
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청 시 Hello World를 실행한다.")
    void test() throws Exception {
        // given
        // PostCreate request = new PostCreate("제목입니다.", "내용입니다.");
        PostCreate request = PostCreate.builder()
                        .title("제목입니다.")
                        .content("내용입니다.")
                        .build();

        String json = objectMapper.writeValueAsString(request); // json 형태로 가공

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 title값은 필수다.")
    void test2() throws Exception {
        // given
        // PostCreate request = new PostCreate("제목입니다.", "내용입니다.");
        PostCreate request = PostCreate.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request); // json 형태로 가공

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 DB에 값이 저장된다.")
    void test3() throws Exception {
        // given
        // PostCreate request = new PostCreate("제목입니다.", "내용입니다.");
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request); // json 형태로 가공

        // when 이런 요청을 받으면
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("/글 1개 조회")
    void test4() throws Exception {
        // given
        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .build();
        postRepository.save(post);

        // expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
    }

    @Test
    @DisplayName("/글 여러개 조회")
    void test5() throws Exception {
        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("조난로그 제목" + i)
                        .content("조난로그 내용" + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        // expected
        // &size=5 같은경우는 페이지 표출개수 기능이 있다면 넘길것
        mockMvc.perform(get("/posts?page=1&sort=id,desc&size=5")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(5)))
                .andExpect(jsonPath("$.[0].id").value(30))
                .andExpect(jsonPath("$.[0].title").value("조난로그 제목30"))
                .andExpect(jsonPath("$.[0].content").value("조난로그 내용30"))
                .andDo(print());

    }
}
