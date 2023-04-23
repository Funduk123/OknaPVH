package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.PasswordForm;
import com.tms.oknapvh.service.UserService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
@Validated
public class PasswordController {

    private final JavaMailSender mailSender;

    private final UserService userService;

    @GetMapping("/forgot-password")
    public String showResetPasswordForm() {
        return "forgot-password.html";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email) {

        // Генерация нового пароля
        String newPassword = RandomString.make(8);

        // Подготовка и асинхронная отправка письма с паролем (без задержки на странице)
        CompletableFuture.runAsync(() -> {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            try {
                helper.setFrom("danik-rebkovets@mail.ru");
                helper.setTo(email);
                helper.setSubject("Сброс пароля");
                helper.setText("<h2>Здравствуйте!</h2>" +
                        "<p>Ваш новый пароль для доступа к FutureWindow: <h2>" + newPassword + "</h2>\n" +
                        "<h3>После входа в аккаунт рекомендуем изменить пароль в личном кабинете.</h3>" +
                        "Если вы считаете, что данное сообщение отправлено вам по ошибке, проигнорируйте его.</p>", true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            mailSender.send(message);
        });

        // Обновление пароля пользователя в базе данных
        userService.updatePassword(email, newPassword);

        return "success-reset.html";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "change-password.html";
    }

    @PostMapping("/change-password")
    public ModelAndView changePassword(@ModelAttribute("passwordForm") @Valid PasswordForm passwordForm, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.setViewName("change-password.html");
        } else {
            userService.changePassword(passwordForm.getUsername(), passwordForm.getOldPassword(), passwordForm.getNewPassword());
            modelAndView.setViewName("success-change.html");
        }
        return modelAndView;
    }

    @GetMapping("/help")
    public String showHelpForm() {
        return "help.html";
    }

    @PostMapping("/help")
    public String help() {
        return "redirect:/store/help";
    }

}
