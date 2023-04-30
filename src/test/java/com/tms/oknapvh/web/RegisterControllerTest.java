package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    public void showRegistrationForm() throws Exception {
        mockMvc.perform(get("/store/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration.html"));
    }

    @Test
    @WithAnonymousUser
    @Transactional
    public void testRegistration() throws Exception {
        var user = new UserDto();
        user.setUsername("testUsername");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setEmail("test@mail.ru");
        user.setPhone("+375332221122");
        user.setPassword("testPassword");

        mockMvc.perform(post("/store/register").flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-in.html"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    @WithAnonymousUser
    void registrationWithErrors() throws Exception {
        UserDto user = new UserDto();
        user.setUsername("");

        mockMvc.perform(post("/store/register")
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("registration.html"))
                .andExpect(model().attributeExists("user"));
    }
}