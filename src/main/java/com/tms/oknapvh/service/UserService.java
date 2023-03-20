package com.tms.oknapvh.service;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.exception.ValidationException;

import java.util.List;

public interface UserService {

    UserDto saveUser(UserDto userDto) throws ValidationException;

    List<UserDto> getAll();

    UserDto getByLogin(String login);

    void deleteUser(Integer userId);

}
