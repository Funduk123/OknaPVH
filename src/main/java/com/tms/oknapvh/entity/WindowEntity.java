package com.tms.oknapvh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "windows")
public class WindowEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private Integer width;

    private Integer height;

    private String type;

    private String lamination;

    private Integer mountingWidth;

    private Integer cameras;

    private Integer price;

    private String manufacturer;

    private String availability;

    @OneToOne(mappedBy = "window")
    @ToString.Exclude
    private OrderEntity order;

}