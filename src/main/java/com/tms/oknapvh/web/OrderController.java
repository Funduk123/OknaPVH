package com.tms.oknapvh.web;

import com.tms.oknapvh.service.OrderService;
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
public class OrderController {

    private final OrderService service;

    @GetMapping("/admin-orders")
    public ModelAndView getAdminOrders() {
        var allOrders = service.getAll();
        var modelAndView = new ModelAndView("admin-orders.html");
        modelAndView.addObject("allOrders", allOrders);
        return modelAndView;
    }

    @GetMapping("/user-orders")
    public ModelAndView getUserOrders() {
        var allOrders = service.getAll();
        var modelAndView = new ModelAndView("user-orders.html");
        modelAndView.addObject("allOrders", allOrders);
        return modelAndView;
    }

    @PostMapping("/orders/{id}")
    public String createOrder(@PathVariable UUID id) {
        service.createOrder(id);
        return "redirect:/store";
    }

    @PostMapping("/orders/status/{id}")
    public String updateStatus(@PathVariable UUID id, @RequestParam String status) {
        service.updateStatusById(id, status);
        return "redirect:/store/admin-orders";
    }

    @PostMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable UUID id) {
        service.deleteOrder(id);
        return "redirect:/store/admin-orders";
    }

}
