package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.ReviewEntity;
import com.tms.oknapvh.entity.WindowEntity;
import com.tms.oknapvh.exception.WindowNotFoundException;
import com.tms.oknapvh.mapper.ReviewMapper;
import com.tms.oknapvh.mapper.WindowMapper;
import com.tms.oknapvh.repositories.ReviewRepository;
import com.tms.oknapvh.repositories.WindowRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Test
    @Transactional
    @WithMockUser
    public void testShowMainPage() throws Exception {

        var generator = new EasyRandom();

        var windowDto = generator.nextObject(WindowDto.class);
        var windowEntity1 = generator.nextObject(WindowEntity.class);
        var windowEntity2 = generator.nextObject(WindowEntity.class);

        windowEntity2.setHeight(100);
        windowEntity2.setWidth(100);

        windowRepository.save(windowEntity1);
        windowRepository.save(windowEntity2);

        var windowEntityList = List.of(windowEntity1);
        var windowDtoList = windowMapper.windowsEntityToDto(windowEntityList);

        mockMvc.perform(get("/store").flashAttr("window", windowDto))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("main-page.html"))
                .andExpect(model().attribute("windowsWithoutOrder", hasSize(windowDtoList.size())))
                .andExpect(model().attribute("windowsWithoutOrder", equalTo(windowDtoList)));

    }

    @Test
    @Transactional
    @WithMockUser
    public void testDoSearch() throws Exception {

        var windowDto = new WindowDto();
        windowDto.setHeight(200);
        windowDto.setWidth(500);

        var windowEntity1 = new WindowEntity();
        windowEntity1.setHeight(200);
        windowEntity1.setWidth(500);

        var windowEntity2 = new WindowEntity();
        windowEntity2.setHeight(100);
        windowEntity2.setWidth(100);

        windowRepository.save(windowEntity1);
        windowRepository.save(windowEntity2);

        mockMvc.perform(get("/store/search").flashAttr("window", windowDto))
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

        var reviewEntity = new ReviewEntity();
        reviewEntity.setRating(1);
        reviewEntity.setWindowType("Одностворчатое глухое");

        windowRepository.save(windowEntity);
        reviewRepository.save(reviewEntity);

        var windowDto = windowMapper.entityToDto(windowEntity);
        var reviewsEntity = reviewRepository.findAllByWindowType(windowDto.getType());
        var reviewsDto = reviewMapper.reviewsEntityToDto(reviewsEntity);

        mockMvc.perform(get("/store/window-details/{id}", windowEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("window-details.html"))
                .andExpect(model().attribute("window", windowDto))
                .andExpect(model().attribute("reviewsByWindowType", hasSize(1)))
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

    @Test
    @WithMockUser
    public void testSearchByType() throws Exception {

        var windowType = "Одностворчатое глухое";

        var windowEntityList = windowRepository.findByType(windowType);
        var windowDtoList = windowMapper.windowsEntityToDto(windowEntityList);

        mockMvc.perform(get("/store/search/{windowType}", windowType))
                .andExpect(status().isOk())
                .andExpect(view().name("search.html"))
                .andExpect(model().attribute("foundWindows", windowDtoList));

    }

}