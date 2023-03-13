package com.example.foodcloud.controller.core.user;

import com.example.foodcloud.domain.user.service.delete.UserDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user/delete")
public class UserDeleteController {
    private final UserDeleteService userDeleteService;

    @GetMapping("")
    public String get() {
        return "thymeleaf/user/delete";
    }

    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId, String name, String password) {
        userDeleteService.delete(userId, name, password);

        return "thymeleaf/user/login";
    }
}
