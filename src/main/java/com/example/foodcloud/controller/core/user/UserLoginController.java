package com.example.foodcloud.controller.core.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserLoginController {

    @GetMapping("/user/login")
    public String get() {
        return "thymeleaf/user/login";
    }
}
