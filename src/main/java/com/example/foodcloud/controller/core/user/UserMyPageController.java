package com.example.foodcloud.controller.core.user;

import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.security.login.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class UserMyPageController {
    private final UserRepository userRepository;

    @GetMapping("/user/my-page")
    public String get(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getDetails();

        userRepository.findByName(userDetail.getUsername()).ifPresent(user ->
                model.addAttribute("user", user));
        return "thymeleaf/user/my-page";
    }
}
