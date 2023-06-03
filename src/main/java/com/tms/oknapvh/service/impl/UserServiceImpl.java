package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.exception.InvalidUserPasswordException;
import com.tms.oknapvh.exception.UserNotFoundByEmailException;
import com.tms.oknapvh.exception.UserNotFoundException;
import com.tms.oknapvh.mapper.UserMapper;
import com.tms.oknapvh.repositories.UserRepository;
import com.tms.oknapvh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        var userEntity = findBy(userId, "Пользователь с id: " + userId + " не найден");
        userEntity.setAuth(auth);
        repository.save(userEntity);
    }

    @Override
    public UserEntity getById(UUID userId) {
        return findBy(userId, "Пользователь не найден");
    }

    @Override
    public UserEntity getByEmail(String email) {
        return findBy(email, "Пользователь c почтой " + email + " не найден");
    }

    @Override
    @Transactional
    public void updatePassword(String email, String newPassword) {
        var user = findBy(email, "Пользователь c почтой " + email + " не найден");
        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Override
    public boolean checkEmailExists(String email) {
        return repository.findByEmail(email).isPresent();
    }

    @Override
    public boolean checkUsernameExists(String username) {
        return repository.findByUsername(username).isPresent();
    }


    @Override
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        var user = (UserEntity) loadUserByUsername(username);
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
        } else {
            throw new InvalidUserPasswordException("Неверный старый пароль");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + username + " не найден"));
    }

    private <SC> UserEntity findBy(SC searchCriteria, String errorMessage) {
        if (searchCriteria instanceof String) {
            return repository.findByEmail((String) searchCriteria)
                    .orElseThrow(() -> new UserNotFoundByEmailException(errorMessage));
        }
        if (searchCriteria instanceof UUID) {
            return repository.findById((UUID) searchCriteria)
                    .orElseThrow(() -> new UserNotFoundException(errorMessage));
        }
        throw new IllegalArgumentException("Неподдерживаемый тип репозитория: " + repository.getClass());
    }

}
