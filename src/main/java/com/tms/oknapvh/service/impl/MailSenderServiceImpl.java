package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.dto.ContactForm;
import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.service.MailSenderService;
import com.tms.oknapvh.service.UserService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.concurrent.CompletableFuture;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender mailSender;

    private final UserService userService;

    private static final String SUPPORT_SUBJECT = "Сообщение от пользователя";

    private static final String SUPPORT_EMAIL = "danik-rebkovets@mail.ru";

    @Override
    public String sendPasswordResetEmail(String userEmail) {

        // Генерация нового пароля
        var newPassword = RandomString.make(8);

        // Подготовка и асинхронная отправка письма с паролем (без задержки на странице)
        CompletableFuture.runAsync(() -> {
            var mailMessage = mailSender.createMimeMessage();
            var mimeMessageHelper = new MimeMessageHelper(mailMessage);
            try {
                mimeMessageHelper.setFrom(SUPPORT_EMAIL);
                mimeMessageHelper.setTo(userEmail);
                mimeMessageHelper.setSubject("Сброс пароля");
                mimeMessageHelper.setText("<h2>Здравствуйте!</h2>" +
                        "<p>Ваш новый пароль для доступа к FutureWindow: <b>" + newPassword + "</b><br><br>" +
                        "<h4>После входа в аккаунт рекомендуем изменить пароль в личном кабинете.</h4>" +
                        "Если вы считаете, что данное сообщение отправлено вам по ошибке, проигнорируйте его.</p>", true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            mailSender.send(mailMessage);
        });

        return newPassword;
    }

    @Override
    public void sendAnonymousSupportEmail(String email, String message) {

        var user = userService.getByEmail(email);
        var username = user.getUsername();
        var userEmail = user.getEmail();
        var phone = user.getPhone();

        sender(message, username, userEmail, phone);
    }

    @Override
    public void sendLoggedInSupportEmail(String message) {

        var authentication = getContext().getAuthentication();
        var user = (UserEntity) authentication.getPrincipal();
        var userEmail = user.getEmail();
        var username = user.getUsername();
        var phone = user.getPhone();

        sender(message, username, userEmail, phone);
    }

    @Override
    public void sendContactForm(ContactForm contactForm) {
        CompletableFuture.runAsync(() -> {
            var mailSenderMimeMessage = mailSender.createMimeMessage();
            var mimeMessageHelper = new MimeMessageHelper(mailSenderMimeMessage);
            try {
                mimeMessageHelper.setFrom(SUPPORT_EMAIL);
                mimeMessageHelper.setTo(SUPPORT_EMAIL);
                mimeMessageHelper.setSubject(SUPPORT_SUBJECT);
                mimeMessageHelper.setText("Email пользователя: <b>" + contactForm.getEmail() + "</b><br>" +
                        "Номер телефона: " + contactForm.getPhone() + "<br><br>" +
                        "<b>Текст сообщения: </b>" + contactForm.getMessage(), true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            mailSender.send(mailSenderMimeMessage);
        });
    }

    private void sender(String message, String username, String userEmail, String phone) {
        CompletableFuture.runAsync(() -> {
            var mailSenderMimeMessage = mailSender.createMimeMessage();
            var mimeMessageHelper = new MimeMessageHelper(mailSenderMimeMessage);
            try {
                mimeMessageHelper.setFrom(SUPPORT_EMAIL);
                mimeMessageHelper.setTo(SUPPORT_EMAIL);
                mimeMessageHelper.setSubject(SUPPORT_SUBJECT);
                mimeMessageHelper.setText("Логин пользователя: <b>" + username + "</b><br>" +
                        "Email пользователя: <b>" + userEmail + "</b><br>" +
                        "Номер телефона: " + phone + "<br><br>" +
                        "<b>Текст сообщения: </b>" + message, true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            mailSender.send(mailSenderMimeMessage);
        });
    }

}
