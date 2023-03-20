package com.tms.oknapvh.converter;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.WindowEntity;
import org.springframework.stereotype.Component;

@Component
public class WindowConverter {

    public WindowEntity fromWindowDtoToWindowEntity(WindowDto windowDto) {
        return WindowEntity.builder()
                .id(windowDto.getId())
                .model(windowDto.getModel())
                .width(windowDto.getWidth())
                .height(windowDto.getHeight())
                .price(windowDto.getPrice())
                .manufacturer(windowDto.getManufacturer())
                .availability(windowDto.isAvailability())
                .build();
    }

    public WindowDto fromWindowEntityToWindowDto(WindowEntity windowEntity) {
        return WindowDto.builder()
                .id(windowEntity.getId())
                .model(windowEntity.getModel())
                .width(windowEntity.getWidth())
                .height(windowEntity.getHeight())
                .price(windowEntity.getPrice())
                .manufacturer(windowEntity.getManufacturer())
                .availability(windowEntity.isAvailability())
                .build();
    }

}
