package com.tms.oknapvh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "orders", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"window_id"})
})
public class OrderEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private UUID userId;

    private UUID window_id;

    private Integer price;

    private String dateAndTime;

    private String status;

}
