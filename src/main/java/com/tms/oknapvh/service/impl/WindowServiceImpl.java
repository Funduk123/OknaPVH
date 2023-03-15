package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.model.WindowEntity;
import com.tms.oknapvh.repository.WindowRepository;
import com.tms.oknapvh.service.WindowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WindowServiceImpl implements WindowService {

    private final WindowRepository repository;

    @Override
    public List<WindowEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public WindowEntity save(WindowEntity window) {
        return repository.save(window);
    }

    @Override
    public WindowEntity getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        repository.findById(id).ifPresent(repository::delete);
    }

    @Override
    @Transactional
    public WindowEntity update(WindowEntity window) {
        var windowFromDB = repository.findById(window.getId()).orElseThrow(RuntimeException::new);

        windowFromDB.setModel(window.getModel());
        windowFromDB.setHeight(window.getHeight());
        windowFromDB.setWidth(window.getWidth());
        windowFromDB.setPrice(window.getPrice());
        windowFromDB.setManufacturer(window.getManufacturer());
        windowFromDB.setAvailability(window.isAvailability());

        return windowFromDB;
    }

}
