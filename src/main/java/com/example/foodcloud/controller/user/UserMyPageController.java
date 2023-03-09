package com.example.foodcloud.controller.user;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user/my-page")
public class UserMyPageController {
    private final UserRepository userRepository;

    @GetMapping("")
    public String getUserList(@SessionAttribute("userId") Long userId, Model model) {
        System.out.println(userId + "asdfasdfsdfafsdfdsfaf");
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> model.addAttribute("userInfo", value));
        return "thymeleaf/user/my-page";
    }
}
