package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.WindowEntity;
import com.tms.oknapvh.service.WindowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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

    @GetMapping
    public ModelAndView mainPage(@ModelAttribute(name = "window") WindowDto windowDto) {
        var windowsWithoutOrder = windowService.getMatches(windowDto);
        ModelAndView modelAndView = new ModelAndView("main-page.html");
        modelAndView.addObject("windowsWithoutOrder", windowsWithoutOrder);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@ModelAttribute(name = "window") WindowDto windowDto) {
        List<WindowEntity> windowsByMatches = windowService.getMatches(windowDto);
        ModelAndView modelAndView = new ModelAndView("search.html");
        modelAndView.addObject("foundWindows", windowsByMatches);
        return modelAndView;
    }

    @GetMapping("/window-details/{id}")
    public ModelAndView details(@PathVariable(name = "id") UUID id) {
        var window = windowService.getById(id);
        ModelAndView modelAndView = new ModelAndView("window-details.html");
        modelAndView.addObject("window", window);
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView("profile.html");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("user", authentication.getPrincipal());
        return modelAndView;
    }

}
