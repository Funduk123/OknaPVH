package com.tms.oknapvh.dto;

import com.tms.oknapvh.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private UUID id;

    private Integer price;

    private LocalDateTime dateAndTime;

    private OrderStatus status;

    private WindowDto window;

    private UserDto user;

}
