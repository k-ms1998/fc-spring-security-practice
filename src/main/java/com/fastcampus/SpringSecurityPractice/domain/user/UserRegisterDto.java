package com.fastcampus.SpringSecurityPractice.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRegisterDto {

    private String username;
    private String password;

    public UserRegisterDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
