package com.fastcampus.SpringSecurityPractice.domain.notice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeRegisterDto {

    private String title;
    private String content;

    public NoticeRegisterDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
