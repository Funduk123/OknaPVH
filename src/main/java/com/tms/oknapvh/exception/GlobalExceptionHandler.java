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

    @ExceptionHandler(UserNotFoundByEmailException.class)
    public ModelAndView handleUserNotFoundByEmailException(UserNotFoundByEmailException exc) {
        var modelAndView = new ModelAndView("error.html");
        modelAndView.addObject("userNotFoundByEmailException", exc.getMessage());
        modelAndView.addObject("email", exc.getEmail());
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

    @ExceptionHandler(InvalidUserPasswordException.class)
    public ModelAndView handleInvalidUserPasswordException(InvalidUserPasswordException exc) {
        var modelAndView = new ModelAndView("change-password.html");
        modelAndView.addObject("invalidOldPasswordException", exc.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleConstraintViolationException(ConstraintViolationException ex) {
        ModelAndView modelAndView = new ModelAndView("change-password.html");
        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse(ex.getMessage());
        modelAndView.addObject("invalidNewPasswordException", message);
        return modelAndView;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView handleUsernameNotFoundException(UsernameNotFoundException exc) {
        var modelAndView = new ModelAndView("error.html");
        modelAndView.addObject("usernameNotFoundException", exc.getMessage());
        return modelAndView;
    }

}
