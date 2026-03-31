package com.jonanlog.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    // frontend 에 대한 고민
    // SSR -> jsp, thymeleaf, mustache, freemarker
    // SPA ->
    //      vue -> vue + SSR = nuxt => 이걸로 하자
    //      react -> react + SSR = next

    @GetMapping("/posts")
    public String get() {
        return "hello world";
    }

}
