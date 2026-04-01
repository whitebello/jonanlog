package com.jonanlog.api.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 서비스 정책에 맞는 응답 클래스
 * */
@Getter
public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;

    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10)); // 타이틀을 10글자로 끊어서 내려보내달라는 요청이 있다는 가정.
        this.content = content;
    }
}
