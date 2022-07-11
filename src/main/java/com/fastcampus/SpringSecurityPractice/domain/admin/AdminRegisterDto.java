package com.fastcampus.SpringSecurityPractice.domain.admin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminRegisterDto {

    private String adminName;
    private String password;
    private String adminId;

    public AdminRegisterDto(String adminName, String password, String adminId) {
        this.adminName = adminName;
        this.password = password;
        this.adminId = adminId;
    }
}
