package com.fastcampus.SpringSecurityPractice.service;

import com.fastcampus.SpringSecurityPractice.domain.admin.Admin;
import com.fastcampus.SpringSecurityPractice.domain.admin.AdminRegisterDto;
import com.fastcampus.SpringSecurityPractice.exception.AlreadyRegisteredException;
import com.fastcampus.SpringSecurityPractice.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public Admin saveAdmin(AdminRegisterDto adminRegisterDto) {
        String adminName = adminRegisterDto.getAdminName();
        String password = adminRegisterDto.getPassword();
        String adminId = adminRegisterDto.getAdminId();

        if (adminRepository.findByAdminId(adminId) != null) {
            throw new AlreadyRegisteredException();
        }

        Admin admin = Admin.of(adminName, passwordEncoder.encode(password), "ROLE_ADMIN", adminId);
        return adminRepository.save(admin);
    }

    public Admin findByAdminName(String adminName) {
        return adminRepository.findByAdminName(adminName);
    }


}
