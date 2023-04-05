package com.example.foodcloud.controller.advice;

import com.example.foodcloud.enums.KoreanErrorCode;
import com.example.foodcloud.exception.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class NotFoundExceptionAdvice {

    /**
     * NotFound의 익셉션을 처리하는 익셉션 어드바이스
     * 각 익셉션에 따라 오류메세지가 달라짐.
     * 에러 메세지는 Enum으로 처리함.
     * Enum으로 처라힘으로 컨트롤단이 변경되었을 때 최소한의 수정으로 처리할 수 있고
     * 오타로 발생하는 오류를 줄일 수 있음.
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

    @ExceptionHandler(NotFoundOrderJoinGroupException.class)
    public ModelAndView notFoundOrderJoinGroupException() {
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(MODEL_NAME, KoreanErrorCode.ORDER_MAIN_NOT_FOUND);

        return modelAndView;
    }

    @ExceptionHandler(NotFoundOrderMenuException.class)
    public ModelAndView notFoundOrderMenuException() {
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(MODEL_NAME, KoreanErrorCode.ORDER_MENU_NOT_FOUND);

        return modelAndView;
    }
}