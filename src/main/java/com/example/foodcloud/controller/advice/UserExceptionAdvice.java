package com.example.foodcloud.controller.advice;

import com.example.foodcloud.enums.KoreanErrorCode;
import com.example.foodcloud.exception.UserNameDuplicateException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class UserExceptionAdvice {

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ModelAndView wrongOfUserInfo() {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND.getResult());

        return modelAndView;
    }

    @ExceptionHandler(UserNameDuplicateException.class)
    public ModelAndView duplicateOfUserName() {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMsg", KoreanErrorCode.USER_NAME_DUPLICATE.getResult());

        return modelAndView;
    }
}
