package com.fastcampus.SpringSecurityPractice.repository;

import com.fastcampus.SpringSecurityPractice.domain.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
