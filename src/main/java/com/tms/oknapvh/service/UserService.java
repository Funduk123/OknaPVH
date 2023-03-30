package com.tms.oknapvh.service;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.exception.ValidationException;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserEntity saveUser(UserDto userDto) throws ValidationException;

    List<UserDto> getAll();

    UserDto getByLogin(String login);

    void deleteUser(UUID userId);

}
