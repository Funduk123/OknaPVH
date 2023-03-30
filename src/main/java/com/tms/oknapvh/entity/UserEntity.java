package com.tms.oknapvh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "persons", indexes = @Index(unique = true, columnList = "login"))
public class UserEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column
    private String name;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String address;

    @Column
    private Role role;

//    @Column
//    @OneToMany
//    private List<OrderEntity> orders;

}