package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.exception.UserNotFoundException;
import com.tms.oknapvh.mapper.UserMapper;
import com.tms.oknapvh.repositories.UserRepository;
import com.tms.oknapvh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto user) {
        repository.save(mapper.dtoToEntity(user));
    }

    @Override
    @Transactional
    public List<UserDto> getAll() {
        return mapper.usersEntityToDto(repository.findAll());
    }

    @Override
    public void deleteUser(UUID userId) {
        repository.deleteById(userId);
    }

    @Override
    public void updateAuthById(UUID userId, String auth) {
        var userEntity = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userEntity.setAuth(auth);
        repository.save(userEntity);
    }

    @Override
    public boolean check(String username, String currentUsername) {
        return username.equals(currentUsername);
    }

    @Override
    public UserEntity getById(UUID userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    @Transactional
    public void updatePassword(String email, String newPassword) {
        var user = repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundByEmailException(email));
        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Override
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        var user = (UserEntity) loadUserByUsername(username);
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
        } else {
            throw new InvalidUserPasswordException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + username + " не найден"));
    }
}
