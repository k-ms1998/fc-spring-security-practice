package com.fastcampus.SpringSecurityPractice.domain.note;

import com.fastcampus.SpringSecurityPractice.domain.BaseEntity.BaseTimeEntity;
import com.fastcampus.SpringSecurityPractice.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Note extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String content;

    public Note(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public static Note of(User user, String title, String content) {
        return new Note(user, title, content);
    }
}
