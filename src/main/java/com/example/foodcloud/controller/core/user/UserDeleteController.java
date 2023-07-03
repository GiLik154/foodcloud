package com.example.foodcloud.controller.core.user;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.user.service.UserDeleter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user/delete")
public class UserDeleteController {
    private final UserDeleter userDeleter;
    private final UserRepository userRepository;

    @GetMapping("")
    public String get(@SessionAttribute("userId") Long userId, Model model) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        model.addAttribute("userInfo", user);

        return "thymeleaf/user/delete";
    }

    @PostMapping("")
    public String post(String name, String password) {
        userDeleter.delete(name, password);

        return "redirect:/user/my-page";
    }
}
