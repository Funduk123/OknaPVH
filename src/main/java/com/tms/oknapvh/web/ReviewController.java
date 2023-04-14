package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.ReviewDto;
import com.tms.oknapvh.entity.ReviewEntity;
import com.tms.oknapvh.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/reviews/{id}")
    public String createReview(ReviewDto reviewDto, @PathVariable(name = "id") UUID id) {
        reviewService.createReview(reviewDto);
        return "redirect:/store/window-details/" + id;
    }

    @GetMapping("/all-reviews")
    public ModelAndView getAllReviews() {
        var allReviews = reviewService.getAllReviews();
        var modelAndView = new ModelAndView("reviews.html");
        modelAndView.addObject("allReviews", allReviews);
        return modelAndView;
    }

    @PostMapping("/reviews/delete/{reviewId}/{windowId}")
    public String deleteReview(@PathVariable UUID reviewId, @PathVariable UUID windowId) {
        reviewService.deleteReview(reviewId);
        return "redirect:/store/window-details/" + windowId;
    }

}
