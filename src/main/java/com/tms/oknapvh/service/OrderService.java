package com.tms.oknapvh.service;

import com.tms.oknapvh.dto.OrderDto;
import com.tms.oknapvh.entity.OrderEntity;


import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<OrderDto> getAll();

    OrderEntity createOrder(UUID id);

    OrderDto getById(UUID orderId);

    void deleteOrder(UUID orderId);

    List<OrderDto> getByUserId(UUID id);

    List<OrderDto> getByWindowId(UUID id);

}
