package com.tms.oknapvh.dto;

import com.tms.oknapvh.entity.OrderStatus;
import com.tms.oknapvh.entity.WindowEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private UUID id;

    private Integer price;

    private String dateAndTime;

    private OrderStatus status = OrderStatus.NEW;

    private WindowDto window;

    private UserDto user;

}
