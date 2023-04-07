package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.mapper.UserMapper;
import com.tms.oknapvh.repositories.UserRepository;
import com.tms.oknapvh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    @Override
    public UserDto saveUser(UserEntity user) {
        repository.save(user);
        return mapper.entityToDto(user);
    }

    @Override
    @Transactional
    public List<UserDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getByLogin(String username) {
        return repository.findByUsername(username)
                .map(mapper::entityToDto)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteUser(UUID userId) {
        repository.deleteById(userId);
    }

}
