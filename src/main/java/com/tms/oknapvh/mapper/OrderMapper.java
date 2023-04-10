package com.tms.oknapvh.mapper;

import com.tms.oknapvh.dto.OrderDto;
import com.tms.oknapvh.entity.OrderEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {

    OrderEntity dtoToEntity(OrderDto orderDto);

    OrderDto entityToDto(OrderEntity orderEntity);

    List<OrderDto> ordersEntityToDto(List<OrderEntity> orders);



}
