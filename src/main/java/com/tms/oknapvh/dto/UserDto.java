package com.tms.oknapvh.dto;

import com.tms.oknapvh.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private UUID id;

    private String name;

    private String login;

    private String password;

    private String email;

    private String phone;

    private String address;

    private Role role;

    private List<OrderDto> orders;

}
