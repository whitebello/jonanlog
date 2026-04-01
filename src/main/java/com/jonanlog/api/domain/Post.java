package com.jonanlog.api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    public String title;

    @Lob
    public String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
