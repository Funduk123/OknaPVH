package com.tms.oknapvh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WindowDto {

    private Integer id;

    private Integer width;

    private Integer height;

    private String type;

    private String lamination;

    private Integer mountingWidth;

    private Integer cameras;

    private Integer price;

    private String manufacturer;

    private String availability;

}
