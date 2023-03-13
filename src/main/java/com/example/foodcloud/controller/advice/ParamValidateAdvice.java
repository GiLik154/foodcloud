package com.example.foodcloud.controller.advice;

import com.example.foodcloud.enums.KoreanErrorCode;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ParamValidateAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView methodArgumentNotValidException() {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMsg", KoreanErrorCode.METHOD_ARGUMENT.getResult());

        return modelAndView;
    }
}
