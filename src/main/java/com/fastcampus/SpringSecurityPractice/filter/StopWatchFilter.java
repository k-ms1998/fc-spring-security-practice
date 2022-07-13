package com.fastcampus.SpringSecurityPractice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 사용자 정의 Custom Filter #1
 * 각 요청에 대해서, 요청이 들어온 시점부터 끝날때까지 걸린 시간을 기록
 */
@Slf4j
public class StopWatchFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        StopWatch stopWatch = new StopWatch(request.getServletPath());

        stopWatch.start();
        filterChain.doFilter(request, response);
        stopWatch.stop();
        log.info(stopWatch.shortSummary());
    }
}
