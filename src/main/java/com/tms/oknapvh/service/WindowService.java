package com.tms.oknapvh.service;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.WindowEntity;

import java.util.List;
import java.util.UUID;

public interface WindowService {

    List<WindowDto> getAll();

    WindowEntity saveWindow(WindowDto windowDto);

    void deleteWindow(UUID id);

    WindowDto getById(UUID id);

    List<WindowDto> getBySomething(WindowDto windowDto);

    List<WindowDto> getAllWithoutOrder();

}
