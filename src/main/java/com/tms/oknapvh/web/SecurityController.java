package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.entity.UserRole;
import com.tms.oknapvh.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
@Slf4j
public class SecurityController {

    private final UserService service;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registration() {
        return "registration.html";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("user") UserDto user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setAuth(UserRole.ROLE_USER.name());
        service.saveUser(user);
        return "login.html";
    }

    @GetMapping("/sign-in")
    public String signIn() {
        return "login.html";
    }

}
