package com.example.foodcloud.controller.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        Optional<Object> nullCheckSession = Optional.ofNullable(session.getAttribute("userId"));
//
//        if (!nullCheckSession.isPresent()) {
//            session.setAttribute("isNotLogin", true);
//            response.sendRedirect("/user/login");
//            return false;
//        }
//        session.removeAttribute("isNotLogin");

        return true;
    }


}
