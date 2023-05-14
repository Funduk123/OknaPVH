package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.PasswordForm;
import com.tms.oknapvh.service.MailSenderService;
import com.tms.oknapvh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
@Validated
public class PasswordController {

    private final MailSenderService customMailSender;

    private final UserService userService;

    @GetMapping("/forgot-password")
    public String showResetPasswordForm() {
        return "forgot-password.html";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String userEmail) {
        var newPassword = customMailSender.sendPasswordResetEmail(userEmail);
        userService.updatePassword(userEmail, newPassword);
        return "reset.html";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "change-password.html";
    }

    @PostMapping("/change-password")
    public ModelAndView changePassword(@ModelAttribute("passwordForm") @Valid PasswordForm passwordForm, BindingResult result) {
        var modelAndView = new ModelAndView();
        var username = getContext().getAuthentication().getName();
        userService.changePassword(username, passwordForm.getOldPassword(), passwordForm.getNewPassword());
        modelAndView.setViewName("change.html");
        return modelAndView;
    }

    @GetMapping("/support")
    public String showSupportPage() {
        var authentication = getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        if (principal.equals("anonymousUser")) {
            return "support-anonymous.html";
        } else {
            return "support.html";
        }
    }

    @PostMapping("/support")
    public ModelAndView sendLoggedInMessage(@RequestParam String message) {
        var modelAndView = new ModelAndView("support.html");
        customMailSender.sendLoggedInSupportEmail(message);
        modelAndView.addObject("successMessage", "Ваше сообщение успешно отправлено!");
        return modelAndView;
    }

    @PostMapping("/support-anonymous")
    public ModelAndView sendAnonymousMessage(@RequestParam String message, @RequestParam String email) {
        var modelAndView = new ModelAndView("support-anonymous.html");
        boolean emailExists = userService.checkEmailExists(email);
        if (!emailExists) {
            modelAndView.addObject("emailNotFound", true);
            return modelAndView;
        }
        customMailSender.sendAnonymousSupportEmail(email, message);
        modelAndView.addObject("successMessage", "Ваше сообщение успешно отправлено!");
        return modelAndView;
    }

}