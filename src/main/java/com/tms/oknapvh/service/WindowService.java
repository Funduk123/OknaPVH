package com.tms.oknapvh.service;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.WindowEntity;

import java.util.List;

public interface WindowService {

    List<WindowDto> getAll();

    WindowEntity saveWindow(WindowDto windowDto);

//    WindowDto getByType(String type);

    void deleteWindow(String type);

    List<WindowDto> getBySomething(WindowDto windowDto);

    List<WindowDto> getAllWithoutOrder();

}
