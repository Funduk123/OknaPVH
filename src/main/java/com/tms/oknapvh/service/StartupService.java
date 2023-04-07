package com.tms.oknapvh.service;

import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.entity.UserRole;
import com.tms.oknapvh.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StartupService {

    private final UserRepository repository;

    @PostConstruct
    public void init() {

        var user = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("user")
                .password("user")
                .auth(UserRole.USER)
                .build();

        var admin = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("admin")
                .password("admin")
                .auth(UserRole.ADMIN)
                .build();

        repository.save(user);
        repository.save(admin);
    }
}
