package com.fastcampus.SpringSecurityPractice.service;

import com.fastcampus.SpringSecurityPractice.domain.notice.Notice;
import com.fastcampus.SpringSecurityPractice.domain.notice.NoticeRegisterDto;
import com.fastcampus.SpringSecurityPractice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    /**
     * 모든 공지사항 조회
     *
     * @return 모든 공지사항 List
     */
    public List<Notice> findAll() {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    /**
     * 공지사항 저장
     *
     * @param title   제목
     * @param content 내용
     * @return 저장된 공지사항
     */
    @Transactional
    public Notice saveNotice(String title, String content) {
        return noticeRepository.save(Notice.of(title, content));
    }

    /**
     * 공지사항 삭제
     *
     * @param id ID
     */
    @Transactional
    public void deleteNotice(Long id) {
        noticeRepository.findById(id).ifPresent(noticeRepository::delete);
    }
}
