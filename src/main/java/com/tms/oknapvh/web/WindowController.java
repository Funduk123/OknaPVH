package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.service.WindowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
@Slf4j
public class WindowController {

    private final WindowService service;

    @GetMapping
    public ModelAndView mainPage(@ModelAttribute(name = "window") WindowDto windowDto) {
        var allWindowsByType = service.getAllWithoutOrder();
        var modelAndView = new ModelAndView("store.html");
        modelAndView.addObject("allWindowsByType", allWindowsByType);
        return modelAndView;
    }

    @GetMapping("/redactor")
    public ModelAndView redactor(@ModelAttribute(name = "newWindow") WindowDto windowDto) {
        var allWindows = service.getAll();
        var modelAndView = new ModelAndView("redactor.html");
        modelAndView.addObject("allWindows", allWindows);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@ModelAttribute(name = "window") WindowDto windowDto) {
        var allWindows = service.getBySomething(windowDto);
        var modelAndView = new ModelAndView("search.html");
        modelAndView.addObject("foundWindows", allWindows);
        return modelAndView;
    }

    @PostMapping("/redactor")
    public String save(WindowDto windowDto) {
        service.saveWindow(windowDto);
        log.info("Save window: " + windowDto);
        return "redirect:/store/redactor";
    }

    @GetMapping("/redactor/delete/{id}")
    public String delete(@PathVariable(name = "id") UUID id) {
        service.deleteWindow(id);
        log.info("Delete window by id: " + id);
        return "redirect:/store/redactor";
    }

    @GetMapping("/redactor/update/{id}")
    public ModelAndView update(@PathVariable(name = "id") UUID id) {
        var windowDto = service.getById(id);
        var modelAndView = new ModelAndView("update.html");
        modelAndView.addObject("window", windowDto);
        log.info("Go to update window by id: " + id);
        return modelAndView;
    }

    @PostMapping("/redactor/update/{id}")
    public String update(@PathVariable(name = "id") UUID id, WindowDto windowDto) {
        service.saveWindow(windowDto);
        log.info("Update window");
        return "redirect:/store/redactor";
    }
}
