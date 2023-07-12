package com.example.foodcloud.controller.advice;

import com.example.foodcloud.enums.KoreanErrorCode;
import com.example.foodcloud.exception.NotEnoughPointException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class PointExceptionAdvice {

    /**
     * Point에서 발생하는 오류를 처리하는 컨트롤러 어드바이스
     * thymeleaf/error/error-page 로 리다이렉트 시킴.
     */
    @ExceptionHandler(NotEnoughPointException.class)
    public ModelAndView notEnoughPointException() {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMsg", KoreanErrorCode.NOT_ENOUGH_POINT.getResult());

        return modelAndView;
    }
}
