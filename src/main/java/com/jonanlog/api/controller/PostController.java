package com.jonanlog.api.controller;

import com.jonanlog.api.request.PostCreate;
import com.jonanlog.api.response.PostResponse;
import com.jonanlog.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 등록 API
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        postService.write(request);
    }

    // 단건 조회 API
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long id) {
        return postService.get(id);
    }

    // 다건 조회 API
    // 게시글이 많을 수럭
    @GetMapping("/posts")
    public List<PostResponse> getLists(Pageable pageable) {
        return postService.getList(pageable);
    }

}
