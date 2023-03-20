package com.tms.oknapvh.repository;

import com.tms.oknapvh.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByLogin(String login);

}