package com.tms.oknapvh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
