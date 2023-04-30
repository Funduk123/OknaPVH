package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.ReviewDto;
import com.tms.oknapvh.entity.ReviewEntity;
import com.tms.oknapvh.entity.WindowEntity;
import com.tms.oknapvh.repositories.ReviewRepository;
import com.tms.oknapvh.repositories.WindowRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private WindowRepository windowRepository;

    @Test
    @WithMockUser(roles = {"USER", "ADMIN", "SUPER_ADMIN"})
    @Transactional
    public void createReview() throws Exception {

        var windowType = "Одностворчатое глухое";

        var reviewDto = new ReviewDto();
        reviewDto.setWindowType(windowType);
        reviewDto.setRating(5);

        var windowEntity = new WindowEntity();
        windowEntity.setType(windowType);

        windowRepository.save(windowEntity);
        var reviewsBefore = reviewRepository.findAllByWindowType(windowType);

        mockMvc.perform(post("/store/reviews/{id}", windowEntity.getId()).flashAttr("reviewDto", reviewDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/store/window-details/" + windowEntity.getId()));

        var reviewsAfter = reviewRepository.findAllByWindowType(windowType);

        assertEquals(reviewsBefore.size() + 1, reviewsAfter.size());

    }

    @Test
    public void getAllReviews() throws Exception {
        var allReviews = reviewRepository.findAll();
        mockMvc.perform(get("/store/all-reviews"))
                .andExpect(status().isOk())
                .andExpect(view().name("reviews.html"))
                .andExpect(model().attributeExists("allReviews"))
                .andExpect(model().attribute("allReviews", allReviews));
    }

    @Test
    public void deleteReview() throws Exception {

        var windowType = "Одностворчатое глухое";

        var windowEntity = new WindowEntity();
        windowEntity.setType(windowType);

        var reviewEntity = new ReviewEntity();
        reviewEntity.setWindowType(windowType);
        reviewEntity.setRating(5);

        windowRepository.save(windowEntity);
        reviewRepository.save(reviewEntity);

        var reviewsBefore = reviewRepository.findAllByWindowType(windowType);

        mockMvc.perform(post("/store/reviews/delete/{reviewId}/{windowId}", reviewEntity.getId(), windowEntity.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/store/window-details/" + windowEntity.getId()));

        var reviewsAfter = reviewRepository.findAllByWindowType(windowType);

        assertEquals(reviewsBefore.size() - 1, reviewsAfter.size());
    }

}