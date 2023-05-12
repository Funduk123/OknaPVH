package com.tms.oknapvh.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleUserNotFoundException(UserNotFoundException exc) {
        var modelAndView = new ModelAndView("error.html");
        modelAndView.addObject("userNotFoundException", exc.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(UserNotFoundByEmailException.class)
    public ModelAndView handleUserNotFoundByEmailException(UserNotFoundByEmailException exc) {
        var modelAndView = new ModelAndView("error.html");
        modelAndView.addObject("userNotFoundByEmailException", exc.getMessage());
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
        return modelAndView;
    }

    @ExceptionHandler(WindowNotFoundException.class)
    public ModelAndView handleWindowNotFoundException(WindowNotFoundException exc) {
        var modelAndView = new ModelAndView("error.html");
        modelAndView.addObject("windowNotFoundException", exc.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(InvalidUserPasswordException.class)
    public ModelAndView handleInvalidUserPasswordException(InvalidUserPasswordException exc) {
        var modelAndView = new ModelAndView("change-password.html");
        modelAndView.addObject("invalidOldPasswordException", exc.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleConstraintViolationException(ConstraintViolationException exc) {
        var modelAndView = new ModelAndView("change-password.html");
        var message = exc.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse(exc.getMessage());
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
