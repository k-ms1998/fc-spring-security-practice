package com.fastcampus.SpringSecurityPractice.jwt;

/**
 * JWT 기본 설정값
 * ex: 만료 시간 등
 */
public class JwtProperties {
    public static final Integer EXPIRATION_TIME = 600000; // 10 Minutes
    public static final String COOKIE_NAME = "JWT-AUTH";
}
