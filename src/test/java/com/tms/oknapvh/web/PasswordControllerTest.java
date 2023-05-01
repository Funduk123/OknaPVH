package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.PasswordForm;
import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.exception.InvalidUserPasswordException;
import com.tms.oknapvh.exception.UserNotFoundByEmailException;
import com.tms.oknapvh.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PasswordControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(roles = "ANONYMOUS")
    public void testShowResetPasswordForm() throws Exception {
        mockMvc.perform(get("/store/forgot-password"))
                .andExpect(status().isOk())
                .andExpect(view().name("forgot-password.html"));
    }

    @Test
    @Transactional
    public void testResetPassword_Success() throws Exception {

        var user = new UserEntity();
        user.setPassword("oldPassword");
        user.setEmail("test@example.com");

        userRepository.save(user);

        var userPassword = user.getPassword();

        mockMvc.perform(post("/store/reset-password").param("email", "test@example.com"))
                .andExpect(status().isOk());

        var updatedUser = userRepository.findByEmail("test@example.com").orElseThrow();
        assertThat(updatedUser.getPassword()).isNotEqualTo(userPassword);
    }

    @Test
    @Transactional
    public void testResetPassword_ThrowUserNotFoundByEmailException() throws Exception {

        var testEmail = "test@example.com";

        mockMvc.perform(post("/store/reset-password").param("email", testEmail))
                .andExpect(status().isOk())
                .andExpect(view().name("error.html"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundByEmailException))
                .andExpect(result -> assertEquals("Пользователь c почтой " + testEmail + " не найден", result.getResolvedException().getMessage()));
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN", "SUPER_ADMIN"})
    public void testShowChangePasswordForm() throws Exception {
        mockMvc.perform(get("/store/change-password"))
                .andExpect(status().isOk())
                .andExpect(view().name("change-password.html"));
    }

    @Test
    @Transactional
    @WithMockUser(roles = {"USER", "ADMIN", "SUPER_ADMIN"})
    public void testChangePassword_Success() throws Exception {

        var oldPassword = "oldPassword";
        var newPassword = "newPassword";

        var user = new UserEntity();
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode(oldPassword));
        userRepository.save(user);

        var passwordForm = new PasswordForm();
        passwordForm.setUsername(user.getUsername());
        passwordForm.setOldPassword(oldPassword);
        passwordForm.setNewPassword(newPassword);

        mockMvc.perform(post("/store/change-password").flashAttr("passwordForm", passwordForm))
                .andExpect(status().isOk())
                .andExpect(view().name("success-change.html"));

        var updatedUser = userRepository.findByUsername(user.getUsername()).orElseThrow();
        assertTrue(passwordEncoder.matches(newPassword, updatedUser.getPassword()));
    }

    @Test
    @Transactional
    @WithMockUser(roles = {"USER", "ADMIN", "SUPER_ADMIN"})
    void testChangePassword_ThrowInvalidUserPasswordException() throws Exception {

        var user = new UserEntity();
        user.setUsername("test");
        user.setPassword("test");
        userRepository.save(user);

        var passwordForm = new PasswordForm();
        passwordForm.setUsername("test");
        passwordForm.setOldPassword("testOldPassword");
        passwordForm.setNewPassword("testNewPassword");

        mockMvc.perform(post("/store/change-password").flashAttr("passwordForm", passwordForm))
                .andExpect(status().isOk())
                .andExpect(view().name("change-password.html"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidUserPasswordException))
                .andExpect(result -> assertEquals("Неверный старый пароль", result.getResolvedException().getMessage()));
    }

    @Test
    public void testShowHelpForm() {


    }

    @Test
    public void testHelp() {


    }
}