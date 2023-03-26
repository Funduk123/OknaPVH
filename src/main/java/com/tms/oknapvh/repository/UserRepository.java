package com.tms.oknapvh.repository;

import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.entity.WindowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findByLogin(String login);

}