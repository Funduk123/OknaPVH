package com.tms.oknapvh.exception;

public class UserNotFoundByEmailException extends RuntimeException {

    public UserNotFoundByEmailException(String message) {
        super(message);
    }

}
