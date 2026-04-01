package com.jonanlog.api.controller;

import com.jonanlog.api.domain.Post;
import com.jonanlog.api.request.PostCreate;
import com.jonanlog.api.response.PostResponse;
import com.jonanlog.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        postService.write(request);
    }

    // Request 클래스 -> 요청 / validation
    // Response 클래스 // 서비스 정책에 맞는 클래스
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long id) {
        return postService.get(id);
    }

}
