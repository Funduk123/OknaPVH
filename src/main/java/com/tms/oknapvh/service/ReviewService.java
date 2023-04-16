package com.tms.oknapvh.service;

import com.tms.oknapvh.dto.ReviewDto;
import com.tms.oknapvh.entity.ReviewEntity;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    List<ReviewDto> getReviewsByWindowType(String windowType);

    void createReview(ReviewDto reviewDto);

    void deleteReview(UUID reviewId);

    List<ReviewEntity> getAllReviews();

}
