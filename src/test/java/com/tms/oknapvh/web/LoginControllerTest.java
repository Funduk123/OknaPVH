package com.tms.oknapvh.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    public void testSignIn() throws Exception {
        mockMvc.perform(get("/store/sign-in"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-in.html"));
    }

    @Test
    @WithAnonymousUser
    public void testSignIn_WithErrors() throws Exception {
        mockMvc.perform(get("/store/sign-in").param("error", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-in.html"))
                .andExpect(model().attribute("error", "Неверный логин или пароль"));
    }

}