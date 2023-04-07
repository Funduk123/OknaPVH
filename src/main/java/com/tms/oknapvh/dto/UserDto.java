package com.tms.oknapvh.dto;

import com.tms.oknapvh.entity.UserRole;
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

    private String username;

    private String password;

    private String email;

    private String phone;

    private UserRole userRole;

    private List<OrderDto> orders;

}
