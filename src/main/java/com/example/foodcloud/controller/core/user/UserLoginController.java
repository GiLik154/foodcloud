package com.example.foodcloud.controller.core.user;

import com.example.foodcloud.domain.user.service.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequiredArgsConstructor
public class UserLoginController {
    private final UserLogin userLogin;

    @GetMapping("/user/login")
    public String get() {
        return "thymeleaf/user/login";
    }
}
