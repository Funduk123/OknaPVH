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
    public ModelAndView windowsPVH(@ModelAttribute(name = "newWindow") WindowDto windowDto) {
        var allWindows = service.getAll();
        var modelAndView = new ModelAndView("store.html");
        modelAndView.addObject("allWindows", allWindows);
        log.info("All windows");
        return modelAndView;
    }

    @PostMapping
    public String save(WindowDto windowDto) {
        service.saveWindow(windowDto);
        log.info("Save window: " + windowDto);
        return "redirect:/store";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Integer id) {
        service.deleteWindow(id);
        log.info("Delete window by id: " + id);
        return "redirect:/store";
    }

    @GetMapping("/info/{id}")
    public ModelAndView info(@PathVariable(name = "id") Integer id) {
        var windowDto = service.getById(id);
        var modelAndView = new ModelAndView("update.html");
        modelAndView.addObject("window", windowDto);
        log.info("Info about window by id: " + id);
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable(name = "id") Integer id, WindowDto windowDto) {
        service.saveWindow(windowDto);
        log.info("Update window");
        return "redirect:/store";
    }

}
