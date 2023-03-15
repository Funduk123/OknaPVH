package com.tms.oknapvh.service;

import com.tms.oknapvh.model.WindowEntity;

import java.util.List;

public interface WindowService {

    List<WindowEntity> getAll();

    WindowEntity save(WindowEntity window);

    WindowEntity getById(Integer id);

    void delete(Integer id);

    WindowEntity update(WindowEntity window);

}
