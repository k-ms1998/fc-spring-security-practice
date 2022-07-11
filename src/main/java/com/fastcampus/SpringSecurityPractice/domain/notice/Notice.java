package com.fastcampus.SpringSecurityPractice.domain.notice;

import com.fastcampus.SpringSecurityPractice.domain.BaseEntity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;

    public Notice(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Notice of(String title, String content) {
        return new Notice(title, content);
    }
}
