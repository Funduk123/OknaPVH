package com.tms.oknapvh.mapper;

import com.tms.oknapvh.dto.OrderDto;
import com.tms.oknapvh.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {

    OrderEntity dtoToEntity(OrderDto orderDto);

    OrderDto entityToDto(OrderEntity orderEntity);

}
