package com.tms.oknapvh.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/sign-in")
    public ModelAndView signIn(@RequestParam(value = "error", required = false) String error) {
        var modelAndView = new ModelAndView("sign-in.html");
        if (error != null) {
            modelAndView.addObject("error", "Неверный логин или пароль");
        }
        return modelAndView;
    }

}

