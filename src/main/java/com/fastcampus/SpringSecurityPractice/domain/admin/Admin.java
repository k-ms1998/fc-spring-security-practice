package com.fastcampus.SpringSecurityPractice.domain.admin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue
    private Long id;

    private String adminName;
    private String password;
    private String authority;

    @Column(unique = true)
    private String adminId;

    public Admin(String adminName, String password, String authority, String adminId) {
        this.adminName = adminName;
        this.password = password;
        this.authority = authority;
        this.adminId = adminId;
    }

    public static Admin of(String adminName, String password, String authority, String adminId) {
        return new Admin(adminName, password, authority, adminId);
    }

    public boolean isAdmin() {
        return authority.equals("ROLE_ADMIN");
    }
}
