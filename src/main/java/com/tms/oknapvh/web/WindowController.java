package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.service.WindowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
@Slf4j
public class WindowController {

    private final WindowService service;

    @GetMapping
    public ModelAndView mainPage(@ModelAttribute(name = "window") WindowDto windowDto) {
        var allWindows = service.getAll();
        var modelAndView = new ModelAndView("store.html");
        modelAndView.addObject("allWindows", allWindows);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@ModelAttribute(name = "window") WindowDto windowDto) {
        var allWindows = service.getBySpecification(windowDto);
        var modelAndView = new ModelAndView("search.html");
        modelAndView.addObject("foundWindows", allWindows);
        return modelAndView;
    }

    @GetMapping("/redactor")
    public ModelAndView redactor(@ModelAttribute(name = "newWindow") WindowDto windowDto) {
        var allWindows = service.getAll();
        var modelAndView = new ModelAndView("redactor.html");
        modelAndView.addObject("allWindows", allWindows);
        return modelAndView;
    }

    @PostMapping("/redactor")
    public String save(WindowDto windowDto) {
        service.saveWindow(windowDto);
        log.info("Save window: " + windowDto);
        return "redirect:/store/redactor";
    }

    @GetMapping("/redactor/delete/{id}")
    public String delete(@PathVariable(name = "id") Integer id) {
        service.deleteWindow(id);
        log.info("Delete window by id: " + id);
        return "redirect:/store/redactor";
    }

    @GetMapping("/redactor/update/{id}")
    public ModelAndView info(@PathVariable(name = "id") Integer id) {
        var windowDto = service.getById(id);
        var modelAndView = new ModelAndView("update.html");
        modelAndView.addObject("window", windowDto);
        log.info("Info about window by id: " + id);
        return modelAndView;
    }

    @PostMapping("/redactor/update/{id}")
    public String update(@PathVariable(name = "id") Integer id, WindowDto windowDto) {
        service.saveWindow(windowDto);
        log.info("Update window");
        return "redirect:/store/redactor";
    }
}
