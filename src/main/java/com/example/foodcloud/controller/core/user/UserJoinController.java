package com.example.foodcloud.controller.core.user;

import com.example.foodcloud.controller.core.user.dto.UserJoinControllerDto;
import com.example.foodcloud.domain.user.service.UserRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user/join")
public class UserJoinController {
    private final UserRegister userRegister;

    @GetMapping("")
    public String get() {
        return "thymeleaf/user/login";
    }

    @PostMapping("")
    public String post(@Valid UserJoinControllerDto userJoinControllerDto, Model model) {

        userRegister.register(userJoinControllerDto.convert());

        model.addAttribute("isJoin", true);

        return "thymeleaf/user/join-check";
    }
}
