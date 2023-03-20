package com.tms.oknapvh.dto;

import com.tms.oknapvh.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;

    private String name;

    private String login;

    private String password;

    private String email;

    private String phone;

    private String address;

    private Role role;

}
