package com.tms.oknapvh.exception;

public class InvalidUserPasswordException extends RuntimeException {

    public InvalidUserPasswordException() {
        super("Неверный старый пароль");
    }

}
