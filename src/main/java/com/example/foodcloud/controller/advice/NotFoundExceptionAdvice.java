package com.example.foodcloud.controller.advice;

import com.example.foodcloud.enums.KoreanErrorCode;
import com.example.foodcloud.exception.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class NotFoundExceptionAdvice {

    /**
     * NotFound의 익셉션을 처리하는 컨트롤러 어드바이스
     * thymeleaf/error/error-page 로 리다이렉트 시킴.
     */
    private static final String VIEW_PAGE = "thymeleaf/error/error-page";
    private static final String MODEL_NAME = "errorMsg";

    @ExceptionHandler(NotFoundRestaurantException.class)
    public ModelAndView notFoundRestaurantException() {
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(MODEL_NAME, KoreanErrorCode.RESTAURANT_NOT_FOUND);

        return modelAndView;
    }

    @ExceptionHandler(NotFoundFoodMenuException.class)
    public ModelAndView notFoundFoodMenuException() {
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(MODEL_NAME, KoreanErrorCode.FOOD_MENU_NOT_FOUND);

        return modelAndView;
    }

    @ExceptionHandler({NotFoundBankAccountException.class, NotFoundBankCodeException.class})
    public ModelAndView notFoundBankAccountException() {
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(MODEL_NAME, KoreanErrorCode.BANK_NOT_FOUND);

        return modelAndView;
    }

    @ExceptionHandler(NotFoundGroupByListException.class)
    public ModelAndView notFoundOrderJoinGroupException() {
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(MODEL_NAME, KoreanErrorCode.GROUP_BY_LIST_NOT_FOUND);

        return modelAndView;
    }

    @ExceptionHandler(NotFoundOrderMenuException.class)
    public ModelAndView notFoundOrderMenuException() {
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(MODEL_NAME, KoreanErrorCode.ORDER_MENU_NOT_FOUND);

        return modelAndView;
    }
}