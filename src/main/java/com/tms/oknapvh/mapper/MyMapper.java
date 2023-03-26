package com.tms.oknapvh.mapper;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.entity.WindowEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MyMapper {

    WindowEntity dtoToEntity(WindowDto windowDto);

    WindowDto entityToDto(WindowEntity windowEntity);

    UserEntity dtoToEntity(UserDto userDto);

    UserDto entityToDto(UserEntity userEntity);

}
