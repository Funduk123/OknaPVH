package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.exception.ValidationException;
import com.tms.oknapvh.mapper.UserMapper;
import com.tms.oknapvh.repositories.UserRepository;
import com.tms.oknapvh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    @Override
    public UserDto saveUser(UserDto userDto) throws ValidationException {
        validateUserDto(userDto);
        var userEntity = mapper.dtoToEntity(userDto);
        repository.save(userEntity);
        return mapper.entityToDto(userEntity);
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
    public UserDto getByLogin(String login) {
        return repository.findByLogin(login)
                .map(mapper::entityToDto)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteUser(UUID userId) {
        repository.deleteById(userId);
    }

    private void validateUserDto(UserDto userDto) throws ValidationException {
        if (isNull(userDto)) {
            throw new ValidationException("Object user is null");
        }
        if (isNull(userDto.getLogin()) || userDto.getLogin().isEmpty()) {
            throw new ValidationException("Login is empty");
        }
    }

}
