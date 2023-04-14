package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.UserDto;
import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.service.ReviewService;
import com.tms.oknapvh.service.UserService;
import com.tms.oknapvh.service.WindowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class MainController {

    private final WindowService windowService;

    private final UserService userService;

    private final ReviewService reviewService;

    @GetMapping
    public ModelAndView mainPage(@ModelAttribute(name = "window") WindowDto windowDto) {
        var windowsWithoutOrder = windowService.getMatches(windowDto);
        var modelAndView = new ModelAndView("main-page.html");
        modelAndView.addObject("windowsWithoutOrder", windowsWithoutOrder);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@ModelAttribute(name = "window") WindowDto windowDto) {
        var windowsByMatches = windowService.getMatches(windowDto);
        var modelAndView = new ModelAndView("search.html");
        modelAndView.addObject("foundWindows", windowsByMatches);
        return modelAndView;
    }

    @GetMapping("/window-details/{id}")
    public ModelAndView details(@PathVariable(name = "id") UUID id) {

        var window = windowService.getById(id);
        var modelAndView = new ModelAndView("window-details.html");
        modelAndView.addObject("window", window);
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView profile() {
        var modelAndView = new ModelAndView("profile.html");
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("user", authentication.getPrincipal());
        return modelAndView;
    }

}
