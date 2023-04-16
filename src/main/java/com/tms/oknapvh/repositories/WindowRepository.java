package com.tms.oknapvh.repositories;

import com.tms.oknapvh.entity.WindowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface WindowRepository extends JpaRepository<WindowEntity, UUID>, JpaSpecificationExecutor<WindowEntity> {

    List<WindowEntity> findByType(String type);

}