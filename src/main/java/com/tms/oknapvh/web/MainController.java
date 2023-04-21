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
    public ModelAndView details(@PathVariable(name = "id") UUID windowId) {

        var windowDto = windowService.getById(windowId);
        var modelAndView = new ModelAndView("window-details.html");
        modelAndView.addObject("window", windowDto);

        var windowType = windowDto.getType();
        var reviewsByWindowType = reviewService.getReviewsByWindowType(windowType);
        modelAndView.addObject("reviewsByWindowType", reviewsByWindowType);

        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView profile() {
        var modelAndView = new ModelAndView("profile.html");
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("user", authentication.getPrincipal());
        return modelAndView;
    }

    @GetMapping("/profile/{username}")
    public ModelAndView userProfile(@PathVariable String username) {
        var user = (UserEntity) userService.loadUserByUsername(username);
        var modelAndView = new ModelAndView("user-profile.html");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/search/{windowType}")
    public ModelAndView searchByType(@ModelAttribute(name = "window") WindowDto windowDto, @PathVariable String windowType) {
        var windowsByType = windowService.getByType(windowType);
        var modelAndView = new ModelAndView("search.html");
        modelAndView.addObject("foundWindows", windowsByType);
        return modelAndView;
    }

    @GetMapping("/users-list")
    public ModelAndView showAllUsers() {
        List<UserDto> users = userService.getAll();
        var modelAndView = new ModelAndView("users-list.html");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

}
