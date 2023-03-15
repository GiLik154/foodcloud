package com.example.foodcloud.controller.advice;

import com.example.foodcloud.enums.KoreanErrorCode;
import com.example.foodcloud.exception.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class NotFoundExceptionAdvice {

    private static final String VIEW_PAGE = "thymeleaf/error/error-page";
    private static final String MODEL_NAME = "errorMsg";

    @ExceptionHandler(NotFoundRestaurantException.class)
    public ModelAndView notFoundRestaurantException() {
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(MODEL_NAME, findByKoreanErrorCode("RESTAURANT_NOT_FOUND"));

        return modelAndView;
    }

    @ExceptionHandler(NotFoundFoodMenuException.class)
    public ModelAndView notFoundFoodMenuException() {
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(MODEL_NAME, findByKoreanErrorCode("FOOD_MENU_NOT_FOUND"));

        return modelAndView;
    }

    @ExceptionHandler({NotFoundBankAccountException.class, NotFoundBankCodeException.class})
    public ModelAndView notFoundBankAccountException() {
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(MODEL_NAME, findByKoreanErrorCode("BANK_NOT_FOUND"));

        return modelAndView;
    }

    @ExceptionHandler(NotFoundOrderMainException.class)
    public ModelAndView notFoundOrderMainException() {
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(MODEL_NAME, findByKoreanErrorCode("ORDER_MAIN_NOT_FOUND"));

        return modelAndView;
    }

    @ExceptionHandler(NotFoundOrderMenuException.class)
    public ModelAndView notFoundOrderMenuException() {
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(MODEL_NAME, findByKoreanErrorCode("ORDER_MENU_NOT_FOUND"));

        return modelAndView;
    }

    private String findByKoreanErrorCode(String error) {
        return KoreanErrorCode.findByResult(error);
    }
}