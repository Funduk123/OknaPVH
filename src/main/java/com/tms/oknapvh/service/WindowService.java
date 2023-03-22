package com.tms.oknapvh.service;

import com.tms.oknapvh.dto.WindowDto;

import java.util.List;

public interface WindowService {

    List<WindowDto> getAll();

    WindowDto saveWindow(WindowDto windowDto);

    WindowDto getById(Integer windowId);

    void deleteWindow(Integer windowId);

    List<WindowDto> getBySpecification(WindowDto windowDto);

}
