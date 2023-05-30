package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.ReviewDto;
import com.tms.oknapvh.entity.WindowEntity;
import com.tms.oknapvh.entity.WindowFilter;
import com.tms.oknapvh.exception.WindowNotFoundException;
import com.tms.oknapvh.mapper.WindowMapper;
import com.tms.oknapvh.repositories.WindowRepository;
import com.tms.oknapvh.service.ReviewService;
import com.tms.oknapvh.service.WindowService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WindowRepository windowRepository;

    @Autowired
    private WindowMapper windowMapper;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private WindowService windowService;

    @Test
    @Transactional
    @WithMockUser
    public void testShowMainPage() throws Exception {

        var generator = new EasyRandom();

        var windowFilter = new WindowFilter();
        var windowEntity = generator.nextObject(WindowEntity.class);
        var allWithoutOrder = windowService.getAllWithoutOrder().size();

        windowRepository.save(windowEntity);

        mockMvc.perform(get("/store").flashAttr("window", windowFilter))
                .andExpect(status().isOk())
                .andExpect(view().name("main-page.html"))
                .andExpect(model().attribute("windowsWithoutOrder", hasSize(allWithoutOrder + 1)));
    }

    @Test
    @Transactional
    @WithMockUser
    public void testSearch() throws Exception {

        var generator = new EasyRandom();

        var windowEntity1 = generator.nextObject(WindowEntity.class);
        windowEntity1.setHeight(200);
        windowEntity1.setWidth(500);

        var windowEntity2 = generator.nextObject(WindowEntity.class);
        windowEntity2.setHeight(100);
        windowEntity2.setWidth(100);

        windowRepository.save(windowEntity1);
        windowRepository.save(windowEntity2);

        var windowFilter = new WindowFilter();
        windowFilter.setHeight(200);
        windowFilter.setWidth(500);

        mockMvc.perform(get("/store/search").flashAttr("window", windowFilter))
                .andExpect(status().isOk())
                .andExpect(view().name("search.html"))
                .andExpect(model().attribute("foundWindows", hasSize(1)));

    }

    @Test
    @Transactional
    @WithMockUser
    public void testShowWindowDetails_IfWindowExists() throws Exception {

        var windowEntity = new WindowEntity();
        windowEntity.setType("Одностворчатое глухое");

        var reviewDto = new ReviewDto();
        reviewDto.setRating(1);
        reviewDto.setWindowType("Одностворчатое глухое");

        windowRepository.save(windowEntity);
        reviewService.createReview(reviewDto);

        var windowDto = windowMapper.entityToDto(windowEntity);
        var reviewsDto = reviewService.getReviewsByWindowType(windowDto.getType());

        mockMvc.perform(get("/store/window-details/{id}", windowEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("window-details.html"))
                .andExpect(model().attribute("window", windowDto))
                .andExpect(model().attribute("reviewsByWindowType", reviewsDto));
    }

    @Test
    @WithMockUser
    public void testShowWindowDetails_IfWindowNotExists() throws Exception {
        var windowNotFoundExceptionMessage = "Окно не найдено";
        mockMvc.perform(get("/store/window-details/{id}", UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(view().name("error.html"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof WindowNotFoundException))
                .andExpect(result -> assertEquals(windowNotFoundExceptionMessage, result.getResolvedException().getMessage()));
    }

}