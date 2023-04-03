package com.tms.oknapvh.repositories;

import com.tms.oknapvh.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    List<OrderEntity> findAllByUserId(UUID userId);

}
