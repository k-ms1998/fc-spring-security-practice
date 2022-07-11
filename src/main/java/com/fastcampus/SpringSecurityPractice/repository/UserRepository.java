package com.fastcampus.SpringSecurityPractice.repository;

import com.fastcampus.SpringSecurityPractice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
