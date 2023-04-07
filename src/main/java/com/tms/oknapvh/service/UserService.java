package com.tms.oknapvh.service;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto saveUser(UserEntity user);

    List<UserDto> getAll();

    UserDto getByLogin(String login);

    void deleteUser(UUID userId);

}
