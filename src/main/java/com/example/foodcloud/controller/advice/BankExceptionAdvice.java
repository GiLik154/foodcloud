package com.example.foodcloud.controller.advice;

import com.example.foodcloud.exception.NotFoundBankAccountException;
import com.example.foodcloud.exception.UserNameDuplicateException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class BankExceptionAdvice {

    @ExceptionHandler(NotFoundBankAccountException.class)
    public ModelAndView duplicateOfUserName() {

        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMsg", KoreanErrorCode.NOT_FOUND_BANK);

        return modelAndView;
    }
}
