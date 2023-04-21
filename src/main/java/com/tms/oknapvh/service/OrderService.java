package com.tms.oknapvh.service;

import com.tms.oknapvh.dto.OrderDto;
import com.tms.oknapvh.dto.WindowDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<OrderDto> getAll();

    void createOrder(WindowDto windowDto);

    void deleteOrder(UUID orderId);

    void updateStatusById(UUID orderId, String orderStatus);

    List<OrderDto> getByUserId(UUID userId);

    void cancellationOrder(UUID orderId);

}
