package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.exception.ValidationException;
import com.tms.oknapvh.repository.UserRepository;
import com.tms.oknapvh.converter.UserConverter;
import com.tms.oknapvh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository repository;
    private final UserConverter converter;

    @Override
    public UserDto saveUser(UserDto userDto) throws ValidationException {
        validateUserDto(userDto);
        var userEntity = converter.fromUserDtoToUserEntity(userDto);
        repository.save(userEntity);
        return converter.fromUserEntityToUserDto(userEntity);
    }

    @Override
    public List<UserDto> getAll() {
        return repository.findAll()
                .stream()
                .map(converter::fromUserEntityToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getByLogin(String login) {
        var userEntityFromDb = repository.findByLogin(login);
        if (userEntityFromDb != null) {
            return converter.fromUserEntityToUserDto(userEntityFromDb);
        }
        return null;
    }

    @Override
    public void deleteUser(Integer userId) {
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
