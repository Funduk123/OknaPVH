package com.tms.oknapvh.service;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.WindowFilter;

import java.util.List;
import java.util.UUID;

public interface WindowService {

    void saveWindow(WindowDto windowDto);

    void deleteWindow(UUID id);

    WindowDto getById(UUID id);

    List<WindowDto> getAllWithoutOrder();

    List<WindowDto> getByWindowFilter(WindowFilter windowFilter);

    List<WindowDto> getByType(String windowType);

}
