package com.fastcampus.SpringSecurityPractice.exception;

/**
 * 이미 등록된 User, Admin 이 재등록을 시도할떄 발생하는  Exception
 */
public class AlreadyRegisteredException extends RuntimeException {

    public AlreadyRegisteredException() {
        super("Already Registered");
    }

    public AlreadyRegisteredException(String message) {
        super(message);
    }

}
