package com.tms.oknapvh.web;

import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") UUID userId, Principal principal) {
        var currentUsername = principal.getName();
        var username = service.getById(userId).getUsername();
        if (!username.equals(currentUsername)) {
            service.deleteUser(userId);
        }
        return "redirect:/store/users-list";
    }

    @GetMapping("/profile")
    public ModelAndView showProfile() {
        var modelAndView = new ModelAndView("profile.html");
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (UserEntity) authentication.getPrincipal();
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/profile/{username}")
    public ModelAndView showUserProfile(@PathVariable(name = "username") String username) {
        var user = (UserEntity) service.loadUserByUsername(username);
        var modelAndView = new ModelAndView("user-profile.html");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/user/updateAuth/{id}")
    public String updateStatus(@PathVariable(name = "id") UUID userId, @RequestParam String auth) {
        service.updateAuthById(userId, auth);
        return "redirect:/store/users-list";
    }

    @GetMapping("/users-list")
    public ModelAndView showAllUsers() {
        var users = service.getAll();
        var modelAndView = new ModelAndView("users-list.html");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

}
