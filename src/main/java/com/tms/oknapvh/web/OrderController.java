package com.tms.oknapvh.web;

import com.tms.oknapvh.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService service;

    @GetMapping("/admin-orders")
    public ModelAndView getAll() {
        var allOrders = service.getAll();
        var modelAndView = new ModelAndView("admin_orders.html");
        modelAndView.addObject("allOrders", allOrders);
        return modelAndView;
    }

    @PostMapping("/admin-orders/{type}")
    public String buy(@PathVariable String type) {
        service.createOrder(type);
        return "redirect:/store";
    }

}
