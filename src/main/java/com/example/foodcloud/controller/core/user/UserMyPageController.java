package com.example.foodcloud.controller.core.user;

import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user/my-page")
public class UserMyPageController {
    private final UserRepository userRepository;

    @GetMapping("")
    public String get(@SessionAttribute("userId") Long userId, Model model) {
        userRepository.findById(userId).ifPresent(user ->
                model.addAttribute("userInfo", user));
        return "thymeleaf/user/my-page";
    }
}
