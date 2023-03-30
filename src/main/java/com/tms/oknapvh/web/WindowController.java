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

    @GetMapping("/redactor/delete/{type}")
    public String delete(@PathVariable(name = "type") String type) {
        service.deleteWindow(type);
        log.info("Delete window by type: " + type);
        return "redirect:/store/redactor";
    }

//    @GetMapping("/redactor/update/{type}")
//    public ModelAndView update(@PathVariable(name = "type") String type) {
//        var windowDto = service.getById(type);
//        var modelAndView = new ModelAndView("update.html");
//        modelAndView.addObject("window", windowDto);
//        log.info("Go to update window by type: " + type);
//        return modelAndView;
//    }

    @PostMapping("/redactor/update/{id}")
    public String update(@PathVariable(name = "id") UUID id, WindowDto windowDto) {
        service.saveWindow(windowDto);
        log.info("Update window");
        return "redirect:/store/redactor";
    }
}
