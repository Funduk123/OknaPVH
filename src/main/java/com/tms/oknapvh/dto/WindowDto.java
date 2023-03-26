package com.tms.oknapvh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WindowDto {

    private Integer id;

    private String model;

    private Integer width;

    private Integer height;

    private Double price;

    private String manufacturer;

    private boolean availability;

}
