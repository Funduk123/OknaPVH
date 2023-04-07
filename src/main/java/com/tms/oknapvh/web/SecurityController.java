package com.tms.oknapvh.web;

import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
@Slf4j
public class SecurityController {

    private final UserService service;

    @GetMapping("/register")
    public String registration() {
        return "registration.html";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("user") UserEntity user) {
        service.saveUser(user);
        return "login.html";
    }

    @GetMapping("/sign-in")
    public String signIn() {
        return "login.html";
    }

}
