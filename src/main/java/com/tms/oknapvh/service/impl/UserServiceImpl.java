package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.entity.UserEntity;
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
    public UserEntity getByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
        // null для LoginController, 35 строка
    }

    @Override
    public void deleteUser(UUID userId) {
        repository.deleteById(userId);
    }

    @Override
    public boolean check(String username, String currentUsername) {
        return username.equals(currentUsername);
    }

    @Override
    public UserEntity getById(UUID userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}
