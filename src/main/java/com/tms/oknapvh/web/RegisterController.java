package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.entity.UserRole;
import com.tms.oknapvh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService service;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        var modelAndView = new ModelAndView("registration.html");
        modelAndView.addObject("user", new UserDto());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registration(@ModelAttribute("user") @Valid UserDto user, BindingResult result) {
        var modelAndView = new ModelAndView();

        boolean emailExists = service.checkEmailExists(user.getEmail());
        boolean usernameExists = service.checkUsernameExists(user.getUsername());

        // Проверка, существует ли пользователь с указанным username
        if (usernameExists) {
            result.rejectValue("username", "error.user", "Пользователь с таким логином уже зарегистрирован");
        }

        // Проверка, существует ли пользователь с указанным email
        if (emailExists) {
            result.rejectValue("email", "error.user", "Пользователь с таким email уже зарегистрирован");
        }

        if (result.hasErrors()) {
            modelAndView.setViewName("registration.html");
            modelAndView.addObject("user", user);
        } else {
            var password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            user.setAuth(UserRole.ROLE_USER.name());
            service.saveUser(user);
            modelAndView.setViewName("sign-in.html");
        }
        return modelAndView;
    }

}
