package com.tms.oknapvh.repositories;

import com.tms.oknapvh.entity.WindowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface WindowRepository extends JpaRepository<WindowEntity, UUID>, JpaSpecificationExecutor<WindowEntity> {

    @Modifying
    @Query("DELETE FROM WindowEntity w WHERE w.type = :type")
    void deleteWindowByType(@Param("type") String type);

    @Query("SELECT w FROM WindowEntity w LEFT JOIN OrderEntity o ON w.id = o.window_id WHERE o.window_id IS NULL AND w.type = :type")
    List<WindowEntity> findByType(@Param("type") String type);

    @Query("SELECT w FROM WindowEntity w LEFT JOIN OrderEntity o ON w.id = o.window_id WHERE o.window_id IS NULL")
    List<WindowEntity> findAllWithoutOrder();



}