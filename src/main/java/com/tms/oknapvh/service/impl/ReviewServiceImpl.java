package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.dto.ReviewDto;
import com.tms.oknapvh.entity.ReviewEntity;
import com.tms.oknapvh.mapper.ReviewMapper;
import com.tms.oknapvh.repositories.ReviewRepository;
import com.tms.oknapvh.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

    @Override
    public List<ReviewDto> getReviewsByWindowType(String windowType) {
        var allByWindowType = reviewRepository.findAllByWindowType(windowType);
        return reviewMapper.ordersEntityToDto(allByWindowType);
    }

    @Override
    public void createReview(ReviewDto reviewDto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        var newReview = reviewMapper.dtoToEntity(reviewDto);
        newReview.setAuthor(username);
        reviewRepository.save(newReview);
    }

    @Override
    public void deleteReview(UUID reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<ReviewEntity> getAllReviews() {
        return reviewRepository.findAll();
    }

}
