package com.example.foodcloud.controller.advice;

import com.example.foodcloud.enums.KoreanErrorCode;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import com.example.foodcloud.exception.NotFoundFoodMenuException;
import com.example.foodcloud.exception.NotFoundRestaurantException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class NotFoundExceptionAdvice {

    @ExceptionHandler(NotFoundRestaurantException.class)
    public ModelAndView notFoundRestaurantException() {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMsg", KoreanErrorCode.NOT_FOUND_RESTAURANT.getResult());

        return modelAndView;
    }

    @ExceptionHandler(NotFoundFoodMenuException.class)
    public ModelAndView notFoundFoodMenuException() {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMsg", KoreanErrorCode.NOT_FOUND_FOOD_MENU.getResult());

        return modelAndView;
    }

    @ExceptionHandler(NotFoundBankAccountException.class)
    public ModelAndView notFoundBankAccountException() {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMsg", KoreanErrorCode.NOT_FOUND_BANK.getResult());

        return modelAndView;
    }
}