package com.tms.oknapvh.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleUserNotFoundException(UserNotFoundException exc) {
        var modelAndView = new ModelAndView("error.html");
        modelAndView.addObject("userNotFoundException", exc.getMessage());
        modelAndView.addObject("userId", exc.getUserId());
        return modelAndView;
    }

    @ExceptionHandler(InvalidOrderStatusException.class)
    public ModelAndView handleInvalidOrderStatusException(InvalidOrderStatusException exc) {
        var modelAndView = new ModelAndView("error.html");
        modelAndView.addObject("invalidOrderStatusException", exc.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ModelAndView handleOrderNotFoundException(OrderNotFoundException exc) {
        var modelAndView = new ModelAndView("error.html");
        modelAndView.addObject("orderNotFoundException", exc.getMessage());
        modelAndView.addObject("orderId", exc.getOrderId());
        return modelAndView;
    }

    @ExceptionHandler(WindowNotFoundException.class)
    public ModelAndView handleWindowNotFoundException(WindowNotFoundException exc) {
        var modelAndView = new ModelAndView("error.html");
        modelAndView.addObject("windowNotFoundException", exc.getMessage());
        modelAndView.addObject("userId", exc.getWindowId());
        return modelAndView;
    }

}
