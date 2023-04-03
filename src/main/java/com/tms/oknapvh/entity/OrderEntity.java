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
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private UUID userId;

    private Integer price;

    private String dateAndTime;

    private OrderStatus status = OrderStatus.NEW;

    @OneToOne
    @JoinColumn(name = "window_id")
    private WindowEntity window_id;


}