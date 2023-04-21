package com.tms.oknapvh.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    private final UUID userId;

    public UserNotFoundException(UUID userId) {
        super("Пользователь не найден");
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}