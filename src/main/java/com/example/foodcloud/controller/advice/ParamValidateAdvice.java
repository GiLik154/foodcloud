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
     * Validation에서 발생하는 오류를 처리하는
     * 컨트롤러 어드바이스
     * 에러 메세지는 Enum으로 처리함.
     * Enum으로 처라힘으로 컨트롤단이 변경되었을 때 최소한의 수정으로 처리할 수 있고
     * 오타로 발생하는 오류를 줄일 수 있음.
     */

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, NullPointerException.class})
    public ModelAndView bindException() {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMsg", KoreanErrorCode.METHOD_ARGUMENT.getResult());

        return modelAndView;
    }
}
