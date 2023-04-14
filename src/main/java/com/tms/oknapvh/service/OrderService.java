package com.tms.oknapvh.service;

import com.tms.oknapvh.dto.OrderDto;
import com.tms.oknapvh.dto.WindowDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<OrderDto> getAll();

    void createOrder(WindowDto window);

    void deleteOrder(UUID orderId);

    void updateStatusById(UUID id, String status);

    List<OrderDto> getByUserId(UUID id);

    void cancellationOrder(UUID id);

}
