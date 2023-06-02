package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.PasswordForm;
import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.exception.InvalidUserPasswordException;
import com.tms.oknapvh.exception.UserNotFoundByEmailException;
import com.tms.oknapvh.repositories.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void testShowResetPasswordForm() throws Exception {
        mockMvc.perform(get("/store/forgot-password"))
                .andExpect(status().isOk())
                .andExpect(view().name("forgot-password.html"));
    }

    @Test
    @Transactional
    public void testResetPassword_Success() throws Exception {

        var generator = new EasyRandom();

        var user = generator.nextObject(UserEntity.class);
        user.setEmail("test@example.com");
        userRepository.save(user);

        var userPassword = user.getPassword();
        var userEmail = user.getEmail();

        mockMvc.perform(post("/store/reset-password").param("email", userEmail))
                .andExpect(status().isOk())
                .andExpect(view().name("reset.html"));

        var updatedUser = userRepository.findByEmail(userEmail).orElseThrow();
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
    @WithMockUser
    public void testShowChangePasswordForm() throws Exception {
        mockMvc.perform(get("/store/change-password"))
                .andExpect(status().isOk())
                .andExpect(view().name("change-password.html"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser")
    public void testChangePassword_Success() throws Exception {

        var generator = new EasyRandom();

        var user = generator.nextObject(UserEntity.class);
        var oldPassword = user.getPassword();
        var newPassword = "newPassword";

        user.setUsername("testUser");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        var passwordForm = new PasswordForm();
        passwordForm.setOldPassword(oldPassword);
        passwordForm.setNewPassword(newPassword);

        mockMvc.perform(post("/store/change-password").flashAttr("passwordForm", passwordForm))
                .andExpect(status().isOk())
                .andExpect(view().name("change.html"));

        var updatedUser = userRepository.findByUsername(user.getUsername()).orElseThrow();
        assertTrue(passwordEncoder.matches(newPassword, updatedUser.getPassword()));
    }

    @Test
    @Transactional
    @WithMockUser
    void testChangePassword_ThrowInvalidOldUserPasswordException() throws Exception {

        var user = new UserEntity();
        user.setUsername("test");
        userRepository.save(user);

        var passwordForm = new PasswordForm();
        passwordForm.setOldPassword("testOldPassword");
        passwordForm.setNewPassword("testNewPassword");

        mockMvc.perform(post("/store/change-password").flashAttr("passwordForm", passwordForm))
                .andExpect(status().isOk())
                .andExpect(view().name("change-password.html"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidUserPasswordException))
                .andExpect(result -> assertEquals("Неверный старый пароль", result.getResolvedException().getMessage()));
    }

    @Test
    @Transactional
    @WithMockUser(username = "test")
    void testChangePassword_ThrowConstraintViolationException() throws Exception {

        var user = new UserEntity();
        user.setUsername("test");
        userRepository.save(user);

        var passwordForm = new PasswordForm();
        passwordForm.setNewPassword("123");

        mockMvc.perform(post("/store/change-password").flashAttr("passwordForm", passwordForm))
                .andExpect(status().isOk())
                .andExpect(view().name("change-password.html"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
                .andExpect(result -> assertEquals("changePassword.passwordForm.newPassword: Пароль должен включать не менее 8 символов", result.getResolvedException().getMessage()));
    }

    @Test
    @WithMockUser
    public void testShowSupportPage_LoggedIn() throws Exception {
        mockMvc.perform(get("/store/support"))
                .andExpect(status().isOk())
                .andExpect(view().name("support.html"));
    }

    @Test
    public void testShowSupportPage_Anonymous() throws Exception {
        mockMvc.perform(get("/store/support"))
                .andExpect(status().isOk())
                .andExpect(view().name("support-anonymous.html"));
    }

    @Test
    public void testSendLoggedInMessage_Success() throws Exception {

        var message = "test";
        var attribute = "Ваше сообщение успешно отправлено!";

        var user = new UserEntity();
        user.setEmail("test@example.com");
        user.setUsername("test");
        user.setPhone("+375332221100");

        mockMvc.perform(post("/store/support").with(user(user)).param("message", message))
                .andExpect(status().isOk())
                .andExpect(view().name("support.html"))
                .andExpect(model().attribute("successMessage", attribute));
    }

    @Test
    @Transactional
    public void testSendAnonymousMessage_Success() throws Exception {

        var generator = new EasyRandom();

        var message = "test";
        var email = "test@example.com";
        var attribute = "Ваше сообщение успешно отправлено!";

        var user = generator.nextObject(UserEntity.class);
        user.setEmail(email);
        userRepository.save(user);

        mockMvc.perform(post("/store/support-anonymous").param("message", message).param("email", email))
                .andExpect(status().isOk())
                .andExpect(view().name("support-anonymous.html"))
                .andExpect(model().attribute("successMessage", attribute));
    }

    @Test
    @Transactional
    public void testSendAnonymousMessage_ThrowEmailNotFoundException() throws Exception {
        var message = "test";
        var email = "test@example.com";
        mockMvc.perform(post("/store/support-anonymous").param("message", message).param("email", email))
                .andExpect(status().isOk())
                .andExpect(view().name("support-anonymous.html"))
                .andExpect(model().attribute("emailNotFound", true));
    }

}