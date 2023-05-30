package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.ContactForm;
import com.tms.oknapvh.entity.WindowFilter;
import com.tms.oknapvh.service.MailSenderService;
import com.tms.oknapvh.service.ReviewService;
import com.tms.oknapvh.service.WindowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class MainController {

    private final WindowService windowService;

    private final ReviewService reviewService;

    private final MailSenderService mailSenderService;

    @GetMapping
    public ModelAndView showMainPage(@ModelAttribute(name = "window") WindowFilter windowFilter) {
        var windowsWithoutOrder = windowService.getAllWithoutOrder();
        var modelAndView = new ModelAndView("main-page.html");
        modelAndView.addObject("windowsWithoutOrder", windowsWithoutOrder);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@ModelAttribute(name = "window") WindowFilter windowFilter) {
        var windowsByFilter = windowService.getByWindowFilter(windowFilter);
        var modelAndView = new ModelAndView("search.html");
        modelAndView.addObject("foundWindows", windowsByFilter);
        return modelAndView;
    }

    @GetMapping("/window-details/{id}")
    public ModelAndView showWindowDetails(@PathVariable(name = "id") UUID windowId) {
        var windowDto = windowService.getById(windowId);
        var modelAndView = new ModelAndView("window-details.html");
        modelAndView.addObject("window", windowDto);
        var windowType = windowDto.getType();
        var reviewsByWindowType = reviewService.getReviewsByWindowType(windowType);
        modelAndView.addObject("reviewsByWindowType", reviewsByWindowType);
        return modelAndView;
    }

    @GetMapping("/contacts")
    public ModelAndView showContacts() {
        var modelAndView = new ModelAndView("contacts.html");
        modelAndView.addObject("contactForm", new ContactForm());
        return modelAndView;
    }

    @PostMapping("/contact-form")
    public ModelAndView sendContactForm(@ModelAttribute("contactForm") @Valid ContactForm contactForm, BindingResult result) {
        var modelAndView = new ModelAndView();
        modelAndView.setViewName("contacts.html");
        if (result.hasErrors()) {
            modelAndView.addObject("contactForm", contactForm);
        } else {
            mailSenderService.sendContactForm(contactForm);
            modelAndView.addObject("successMessage", "Ваше сообщение успешно отправлено!");
        }
        return modelAndView;
    }

    @GetMapping("/about-us")
    public String showAboutUsInfo() {
        return "about-us";
    }

}
