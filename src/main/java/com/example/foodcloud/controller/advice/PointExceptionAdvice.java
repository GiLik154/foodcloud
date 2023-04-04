package com.example.foodcloud.controller.advice;

import com.example.foodcloud.enums.KoreanErrorCode;
import com.example.foodcloud.exception.NotEnoughPointException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class PointExceptionAdvice {

    /**
     * Point에서 발생하는 오류를 처리하는
     * 컨트롤러 어드바이스
     * 에러 메세지는 Enum으로 처리함.
     * Enum으로 처라힘으로 컨트롤단이 변경되었을 때 최소한의 수정으로 처리할 수 있고
     * 오타로 발생하는 오류를 줄일 수 있음.
     */
    @ExceptionHandler(NotEnoughPointException.class)
    public ModelAndView notEnoughPointException() {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMsg", KoreanErrorCode.NOT_ENOUGH_POINT.getResult());

        return modelAndView;
    }
}
