package com.example.foodcloud.controller.user;

import com.example.foodcloud.domain.user.service.update.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/update")
public class UserUpdateController {
    private final UserUpdateService userUpdateService;

    @GetMapping("")
    public String update() {
        return "thymeleaf/user/update";
    }

    @PostMapping("")
    public String check(@SessionAttribute("userId") Long userId, String phone) {
        userUpdateService.update(userId, phone);

        return "thymeleaf/user/update";
    }
}
