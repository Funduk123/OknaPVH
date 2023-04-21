package com.tms.oknapvh.exception;

public class InvalidOrderStatusException extends RuntimeException {

    public InvalidOrderStatusException(String orderStatus) {
        super("Нельзя удалить заказ со статусом: " + orderStatus);
    }

}
