package com.tms.oknapvh.repositories;

import com.tms.oknapvh.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {

    List<ReviewEntity> findAllByWindowType(String windowType);

}
