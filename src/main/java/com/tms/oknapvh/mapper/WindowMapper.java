package com.tms.oknapvh.mapper;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.WindowEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WindowMapper {

    WindowEntity dtoToEntity(WindowDto windowDto);

    WindowDto entityToDto(WindowEntity windowEntity);

}
