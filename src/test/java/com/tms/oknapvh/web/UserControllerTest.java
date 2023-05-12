package com.tms.oknapvh.web;

import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.exception.UserNotFoundException;
import com.tms.oknapvh.mapper.UserMapper;
import com.tms.oknapvh.repositories.UserRepository;
import com.tms.oknapvh.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPER_ADMIN"})
    @Transactional
    public void testDelete_Success() throws Exception {
        var userEntity = new UserEntity();
        userEntity.setUsername("test");
        userRepository.save(userEntity);

        long count = userRepository.count();
        mockMvc.perform(post("/store/user/delete/{id}", userEntity.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/store/users-list"));

        assertEquals(count - 1, userRepository.count());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN", "SUPER_ADMIN"})
    public void testShowProfile() throws Exception {

        var user = new UserEntity();
        user.setUsername("test");
        user.setPassword("test");

        mockMvc.perform(get("/store/profile").with(user(user)))
                .andExpect(status().isOk())
                .andExpect(view().name("profile.html"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    @WithMockUser
    @Transactional
    public void testShowUserProfile_IfExists() throws Exception {
        var testUser = new UserEntity();
        testUser.setUsername("testUsername");
        testUser.setPassword("testPassword");
        testUser.setLastName("testLastName");
        testUser.setFirstName("testFirstName");

        userRepository.save(testUser);
        var user = userRepository.findByUsername(testUser.getUsername()).orElse(null);
        mockMvc.perform(get("/store/profile/{username}", testUser.getUsername()))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile.html"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    @WithMockUser
    @Transactional
    public void testShowUserProfile_IfNotExists() throws Exception {
        var testUsername = "NotExists";
        mockMvc.perform(get("/store/profile/{username}", testUsername))
                .andExpect(status().isOk())
                .andExpect(view().name("error.html"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UsernameNotFoundException))
                .andExpect(result -> assertEquals("Пользователь " + testUsername + " не найден", result.getResolvedException().getMessage()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPER_ADMIN"})
    public void testShowAllUsers_accessIsAllowed() throws Exception {
        var userEntityList = userRepository.findAll();
        var userDtoList = userMapper.usersEntityToDto(userEntityList);
        mockMvc.perform(get("/store/users-list"))
                .andExpect(status().isOk())
                .andExpect(view().name("users-list.html"))
                .andExpect(model().attribute("users", userDtoList));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testShowAllUsers_accessDenied() throws Exception {
        mockMvc.perform(get("/store/users-list"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(roles = {"SUPER_ADMIN"})
    public void testUpdateStatus_Success() throws Exception {
        var userEntity = new UserEntity();
        userEntity.setUsername("test");
        userEntity.setAuth("ROLE_USER");
        userRepository.save(userEntity);

        mockMvc.perform(post("/store/user/updateAuth/{id}", userEntity.getId()).param("auth", "ROLE_ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/store/users-list"));

        assertEquals("ROLE_ADMIN", userEntity.getAuth());
    }

    @Test
    @WithMockUser(roles = {"SUPER_ADMIN"})
    public void testUpdateStatus_ThrowUserNotFoundException() throws Exception {
        mockMvc.perform(post("/store/user/updateAuth/{id}", UUID.randomUUID()).param("auth", "ROLE_ADMIN"))
                .andExpect(status().isOk())
                .andExpect(view().name("error.html"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(result -> assertEquals("Пользователь не найден", result.getResolvedException().getMessage()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPER_ADMIN"})
    public void testShowAllUsers() throws Exception {
        var users = userService.getAll();
        mockMvc.perform(get("/store/users-list"))
                .andExpect(status().isOk())
                .andExpect(view().name("users-list.html"))
                .andExpect(model().attribute("users", users));
        assertEquals(users.size(), userRepository.count());
    }

}