package com.fastcampus.SpringSecurityPractice.domain.note;

import com.fastcampus.SpringSecurityPractice.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoteRegisterDto {

    private User user;
    private String title;
    private String content;

    public NoteRegisterDto(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }
}
