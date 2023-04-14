package com.tms.oknapvh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private UUID id;

    private String author;

    private String text;

    private LocalDateTime dateAndTime = LocalDateTime.now();

    private Integer rating;

    private String windowType;

}
