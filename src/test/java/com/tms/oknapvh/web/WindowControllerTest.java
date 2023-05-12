package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.WindowEntity;
import com.tms.oknapvh.repositories.WindowRepository;
import com.tms.oknapvh.service.WindowService;
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
public class WindowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WindowService windowService;

    @Autowired
    private WindowRepository windowRepository;

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPER_ADMIN"})
    public void testShowRedactorPage_accessIsAllowed() throws Exception {
        mockMvc.perform(get("/store/redactor"))
                .andExpect(status().isOk())
                .andExpect(view().name("redactor.html"));
    }

    @Test
    @WithMockUser(roles = {"USER", "ANONYMOUS"})
    public void testShowRedactorPage_accessDenied() throws Exception {
        mockMvc.perform(get("/store/redactor"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPER_ADMIN"})
    @Transactional
    public void testSave_Success() throws Exception {
        var windowDto = new WindowDto();
        long count = windowRepository.count();
        mockMvc.perform(post("/store/redactor").flashAttr("windowDto", windowDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/store/redactor"));
        assertEquals(count + 1, windowRepository.count());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPER_ADMIN"})
    @Transactional
    public void testDelete_Success() throws Exception {

        var windowEntity1 = new WindowEntity();
        var windowEntity2 = new WindowEntity();

        windowRepository.save(windowEntity1);
        windowRepository.save(windowEntity2);

        long count = windowRepository.count();

        mockMvc.perform(post("/store/redactor/delete/{id}", windowEntity1.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/store/redactor"));

        assertEquals(count - 1, windowRepository.count());

    }

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPER_ADMIN"})
    @Transactional
    public void testShowUpdatePage() throws Exception {

        var windowEntity = new WindowEntity();
        windowRepository.save(windowEntity);

        var windowDto = windowService.getById(windowEntity.getId());

        mockMvc.perform(get("/store/redactor/update/{id}", windowEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("update.html"))
                .andExpect(model().attribute("window", windowDto));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPER_ADMIN"})
    @Transactional
    public void testUpdate_Success() throws Exception {

        var windowEntity = new WindowEntity();
        windowRepository.save(windowEntity);

        var windowDto = new WindowDto();
        windowDto.setId(windowEntity.getId());
        windowDto.setPrice(100);

        mockMvc.perform(post("/store/redactor/update/{id}", windowEntity.getId()).flashAttr("windowDto", windowDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/store/redactor"));

        assertEquals(windowEntity.getPrice(), windowDto.getPrice());

    }


}