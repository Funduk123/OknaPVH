package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.converter.WindowConverter;
import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.repository.WindowRepository;
import com.tms.oknapvh.service.WindowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WindowServiceImpl implements WindowService {

    private final WindowRepository repository;

    private final WindowConverter converter;

    @Override
    public List<WindowDto> getAll() {
        return repository.findAll()
                .stream()
                .map(converter::fromWindowEntityToWindowDto)
                .collect(Collectors.toList());
    }

    @Override
    public WindowDto saveWindow(WindowDto windowDto) {
        var windowEntity = converter.fromWindowDtoToWindowEntity(windowDto);
        repository.save(windowEntity);
        return converter.fromWindowEntityToWindowDto(windowEntity);
    }

    @Override
    public WindowDto getById(Integer id) {
        var windowEntityFromDb = repository.findById(id).orElse(null);
        if (windowEntityFromDb != null) {
            return converter.fromWindowEntityToWindowDto(windowEntityFromDb);
        }
        return null;
    }

    @Override
    public void deleteWindow(Integer windowId) {
        repository.deleteById(windowId);
    }

//    @Override
//    @Transactional
//    public WindowDto updateWindow(WindowDto windowDto) {
//        WindowEntity windowEntityFromDb = repository.findById(windowDto.getId()).orElseThrow(RuntimeException::new);
//
//
//
//        windowEntityFromDb.setModel(windowDto.getModel());
//        windowFromDB.setHeight(window.getHeight());
//        windowFromDB.setWidth(window.getWidth());
//        windowFromDB.setPrice(window.getPrice());
//        windowFromDB.setManufacturer(window.getManufacturer());
//        windowFromDB.setAvailability(window.isAvailability());
//
//        return windowFromDB;
//    }

}
