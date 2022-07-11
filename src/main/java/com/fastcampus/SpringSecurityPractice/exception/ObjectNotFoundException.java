package com.fastcampus.SpringSecurityPractice.exception;

/**
 * User, Admin 이 존재하지 않을때 발생하는 Exception
 */
public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException() {
        super("Not Found");
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }

}
