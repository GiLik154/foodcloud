package com.example.foodcloud.controller.advice;

import com.example.foodcloud.enums.KoreanErrorCode;
import com.example.foodcloud.exception.NotFoundRestaurantException;
import com.example.foodcloud.exception.UserNameDuplicateException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class RestaurantExceptionAdvice {

    @ExceptionHandler(NotFoundRestaurantException.class)
    public ModelAndView notFoundRestaurantException() {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMsg", KoreanErrorCode.NOT_FOUND_RESTAURANT.getResult());

        return modelAndView;
    }
}