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
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private Integer price;

    private String dateAndTime;

    private OrderStatus status = OrderStatus.NEW;

    @OneToOne
    @JoinColumn(name = "window_id")
    private WindowEntity window;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


}
