package com.tms.oknapvh.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    // Изменения...
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private Integer price;

    private LocalDateTime dateAndTime;

    private OrderStatus status;

    @OneToOne
    @JoinColumn(name = "window_id")
    private WindowEntity window;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


}
