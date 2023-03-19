package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.service.WindowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/windowsPVH")
@RequiredArgsConstructor
public class WindowController {

    private final WindowService service;

    @GetMapping
    public ModelAndView windowsPVH(@ModelAttribute(name = "newWindow") WindowDto windowDto) {
        var allWindows = service.getAll();
        ModelAndView modelAndView = new ModelAndView("windowsPVH.html");
        modelAndView.addObject("allWindows", allWindows);
        return modelAndView;
    }

    @PostMapping
    public String save(WindowDto windowDto) {
        service.saveWindow(windowDto);
        return "redirect:/windowsPVH";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Integer id) {
        service.deleteWindow(id);
        return "redirect:/windowsPVH";
    }

    @GetMapping("/info/{id}")
    public ModelAndView info(@PathVariable(name = "id") Integer id) {
        WindowDto windowDto = service.getById(id);
        ModelAndView modelAndView = new ModelAndView("update.html");
        modelAndView.addObject("window", windowDto);
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable(name = "id") Integer id, WindowDto windowDto) {
        service.saveWindow(windowDto);
        return "redirect:/windowsPVH";
    }

}
