package com.jonanlog.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostCreate {

    // TODO: 빌더패턴... 꼭 공부해야함
    // ex) 악의적으로 title, content의 값이 바뀌어서 들어갈경우 버그를 찾기 힘들어진다.
    @Builder // 클래스에 작성해도 됨
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 굳이 값을 변경해야하는 경우라면
    //    public PostCreate changeTitle(String title, String content) {
    //        return PostCreate.builder()
    //                .title(title)
    //                .content(content)
    //                .build();
    //    }

    // 빌더의 장점
    // - 가독성에 좋다. (값 생성에 대한 유연함)
    // - 필요한 값만 받을 수 있다. // -> 오버로딩 가능한 조건 찾아보기
    // - 타이틀만 받겠겠다고 하면 생성자 또 만들어야함
    // - 객체의 불변성

    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;

    @NotBlank(message = "컨텐츠를 입력해주세요.")
    private String content;

}
