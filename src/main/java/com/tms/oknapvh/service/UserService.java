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

    void updateAuthById(UUID userId, String auth);

    UserEntity getById(UUID userId);

    UserEntity getByEmail(String email);

    boolean checkEmailExists(String email);

    boolean checkUsernameExists(String username);

    void updatePassword(String email, String password);

    void changePassword(String username, String oldPassword, String newPassword);

}
