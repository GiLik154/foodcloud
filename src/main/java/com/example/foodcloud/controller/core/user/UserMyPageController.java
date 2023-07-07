package com.example.foodcloud.controller.core.user;

import com.example.foodcloud.security.login.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user/my-page")
public class UserMyPageController {

    @GetMapping("")
    public String get(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getDetails();

        model.addAttribute("user", userDetail.getUser());
        return "thymeleaf/user/my-page";
    }
}
