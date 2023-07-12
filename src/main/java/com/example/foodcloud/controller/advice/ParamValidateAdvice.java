package com.example.foodcloud.controller.advice;

import com.example.foodcloud.enums.KoreanErrorCode;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ParamValidateAdvice {

    /**
     * Validation에서 발생하는 오류를 처리하는 컨드롤러 어드바이스
     * thymeleaf/error/error-page 로 리다이렉트 시킴.
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, NullPointerException.class})
    public ModelAndView bindException() {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMsg", KoreanErrorCode.METHOD_ARGUMENT.getResult());

        return modelAndView;
    }
}
