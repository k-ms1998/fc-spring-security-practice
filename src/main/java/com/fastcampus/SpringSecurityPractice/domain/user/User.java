package com.fastcampus.SpringSecurityPractice.domain.user;

import com.fastcampus.SpringSecurityPractice.constant.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private Role authority;

    public User() {
    }

    public User(String username, String password, Role authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public static User of(String username, String password, Role authority) {
        return new User(username, password, authority);
    }


    public boolean isAdmin() {
        return authority.equals(Role.ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
