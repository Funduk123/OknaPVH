package com.tms.oknapvh.exception;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {

    private final UUID orderId;

    public OrderNotFoundException(UUID orderId) {
        super("Заказ не найден");
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }

}
