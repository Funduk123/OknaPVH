package com.tms.oknapvh.service;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    void saveUser(UserDto userDto);

    List<UserDto> getAll();

    void deleteUser(UUID userId);

    boolean check(String username, String currentUsername);

    UserEntity getById(UUID userId);

}
