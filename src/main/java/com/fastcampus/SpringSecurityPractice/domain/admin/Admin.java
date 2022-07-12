package com.fastcampus.SpringSecurityPractice.domain.admin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admins")
public class Admin implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> String.valueOf(authority));
    }

    @Override
    public String getUsername() {
        return this.getAdminName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
