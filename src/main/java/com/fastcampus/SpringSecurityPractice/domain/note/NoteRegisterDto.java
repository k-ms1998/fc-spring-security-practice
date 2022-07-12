package com.fastcampus.SpringSecurityPractice.domain.note;

import com.fastcampus.SpringSecurityPractice.domain.user.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"title", "content"})
public class NoteRegisterDto {

    private User user;
    private String title;
    private String content;

    public NoteRegisterDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public NoteRegisterDto(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void updateUser(User user) {
        this.user = user;
    }
}
