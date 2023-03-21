package com.tms.oknapvh.mapper;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserEntity dtoToEntity(UserDto userDto);

    UserDto entityToDto(UserEntity userEntity);

}
