package com.tms.oknapvh.service;

import com.tms.oknapvh.dto.WindowDto;

import java.util.List;
import java.util.UUID;

public interface WindowService {

    void saveWindow(WindowDto windowDto);

    void deleteWindow(UUID id);

    WindowDto getById(UUID id);

    List<WindowDto> getMatches(WindowDto windowDto);

    List<WindowDto> getByType(String type);

}
