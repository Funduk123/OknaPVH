package com.tms.oknapvh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WindowFilter {

    private UUID id;

    private Integer width;

    private Integer height;

    private String type;

    private String lamination;

    private Integer mountingWidth;

    private Integer cameras;

    private Integer minPrice;

    private Integer maxPrice;

    private String manufacturer;

    private String availability;

}
