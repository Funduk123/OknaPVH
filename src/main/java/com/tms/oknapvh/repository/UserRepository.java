package com.tms.oknapvh.repository;

import com.tms.oknapvh.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {



}