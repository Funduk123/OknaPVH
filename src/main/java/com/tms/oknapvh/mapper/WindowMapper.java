package com.tms.oknapvh.mapper;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.WindowEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfig.class)
public interface WindowMapper {

    WindowEntity dtoToEntity(WindowDto windowDto);

    WindowDto entityToDto(WindowEntity windowEntity);

    List<WindowDto> windowsEntityToDto(List<WindowEntity> windows);

}
