package com.tms.oknapvh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Column
    private Integer width;

    @Column
    private Integer height;

    @Column
    private String type;

    @Column
    private String lamination;

    @Column
    private Integer mountingWidth;

    @Column
    private Integer cameras;

    @Column
    private Integer price;

    @Column
    private String manufacturer;

    @Column
    private String availability;

}