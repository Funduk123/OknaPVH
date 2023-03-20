package com.tms.oknapvh.mapper;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.WindowEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WindowMapper {

    WindowEntity dtoToEntity(WindowDto windowDto);

    WindowDto entityToDto(WindowEntity windowEntity);

}
