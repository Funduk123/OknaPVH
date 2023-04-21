package com.tms.oknapvh.exception;

import java.util.UUID;

public class WindowNotFoundException extends RuntimeException {

    private final UUID windowId;

    public WindowNotFoundException(UUID windowId) {
        super("Окно не найдено");
        this.windowId = windowId;
    }

    public UUID getWindowId() {
        return windowId;
    }
}
