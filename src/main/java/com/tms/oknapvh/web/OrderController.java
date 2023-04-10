package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.OrderDto;
import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.OrderStatus;
import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping("/orders")
    public ModelAndView getAllOrders() {
        var allOrders = service.getAll();
        ModelAndView modelAndView = new ModelAndView("orders.html");
        modelAndView.addObject("allOrders", allOrders);
        return modelAndView;
    }

    @GetMapping("/my-orders")
    public ModelAndView getUserOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        List<OrderDto> userOrders = service.getByUserId(user.getId());

        ModelAndView modelAndView = new ModelAndView("orders.html");
        modelAndView.addObject("userOrders", userOrders);
        return modelAndView;
    }

    @PostMapping("/orders")
    public String createOrder(@ModelAttribute("windowForOrder") WindowDto window) {
        service.createOrder(window);
        return "redirect:/store";
    }

    @PostMapping("/orders/status/{id}")
    public String updateStatus(@PathVariable UUID id, @RequestParam OrderStatus status) {
        service.updateStatusById(id, status);
        return "redirect:/store/orders";
    }

    @PostMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable UUID id) {
        service.deleteOrder(id);
        return "redirect:/store/orders";
    }

    @PostMapping("/orders/cancellation/{id}")
    public String cancellationOrder(@PathVariable UUID id) {
        service.cancellationOrder(id);
        return "redirect:/store/orders";
    }

}
