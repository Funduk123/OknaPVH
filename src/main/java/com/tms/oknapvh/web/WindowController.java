package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.WindowEntity;
import com.tms.oknapvh.service.WindowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
@Slf4j
public class WindowController {

    private final WindowService service;

    @GetMapping
    public ModelAndView mainPage(@ModelAttribute(name = "window") WindowDto windowDto) {
        var windowsWithoutOrder = service.getBySomething(windowDto);
        var modelAndView = new ModelAndView("admin-main-page.html");
        modelAndView.addObject("windowsWithoutOrder", windowsWithoutOrder);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@ModelAttribute(name = "window") WindowDto windowDto) {
        List<WindowEntity> bySomething = service.getBySomething(windowDto);
        var modelAndView = new ModelAndView("admin-search.html");
        modelAndView.addObject("foundWindows", bySomething);
        return modelAndView;
    }

}
