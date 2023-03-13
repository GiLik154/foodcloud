package com.example.foodcloud.controller.user;

import com.example.foodcloud.domain.user.service.login.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@Transactional
@RequiredArgsConstructor
@SessionAttributes("userId")
@RequestMapping("/user/login")
public class UserLoginController {
    private final UserLoginService userLoginService;

    @GetMapping("")
    public String get(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "thymeleaf/user/login";
    }

    @PostMapping("")
    public String post(String name, String password, Model model) {
        model.addAttribute("userId", userLoginService.login(name, password));
        return "redirect:/user/my-page";
    }
}
