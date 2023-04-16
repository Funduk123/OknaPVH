package com.tms.oknapvh.mapper;

import com.tms.oknapvh.dto.ReviewDto;
import com.tms.oknapvh.entity.ReviewEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfig.class)
public interface ReviewMapper {

    ReviewDto entityToDto(ReviewEntity reviewEntity);

    List<ReviewDto> reviewsEntityToDto(List<ReviewEntity> reviews);

    ReviewEntity dtoToEntity(ReviewDto reviewDto);

}
