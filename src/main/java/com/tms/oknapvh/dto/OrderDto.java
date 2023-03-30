package com.tms.oknapvh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private UUID id;

    private UUID userId;

    private UUID window_id;

    private Integer price;

    private String dateAndTime;

    private String status;

}
