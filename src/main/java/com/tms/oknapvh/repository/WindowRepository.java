package com.tms.oknapvh.repository;

import com.tms.oknapvh.entity.WindowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WindowRepository extends JpaRepository<WindowEntity, Integer>, JpaSpecificationExecutor<WindowEntity> {
}