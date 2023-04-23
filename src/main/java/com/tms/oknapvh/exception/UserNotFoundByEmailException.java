package com.tms.oknapvh.exception;

public class UserNotFoundByEmailException extends RuntimeException {

    private final String email;

    public UserNotFoundByEmailException(String email) {
        super("Пользователь c почтой " + email + " не найден");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
