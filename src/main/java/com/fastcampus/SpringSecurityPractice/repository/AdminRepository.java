package com.fastcampus.SpringSecurityPractice.repository;

import com.fastcampus.SpringSecurityPractice.domain.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByAdminId(String adminId);
}
