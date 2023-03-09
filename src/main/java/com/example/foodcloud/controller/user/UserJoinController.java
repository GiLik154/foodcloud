package com.example.foodcloud.controller.user;

import com.example.foodcloud.controller.user.dto.UserJoinControllerDto;
import com.example.foodcloud.domain.user.service.join.UserJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user/join")
public class UserJoinController {
    private final UserJoinService userJoinService;

    @GetMapping("")
    public String join() {
        return "thymeleaf/user/login";
    }

    @PostMapping("")
    public String check(@Valid UserJoinControllerDto userJoinControllerDto, Model model) {

        userJoinService.join(userJoinControllerDto.convertDto());

        model.addAttribute("isJoin", true);

        return "thymeleaf/user/join-check";
    }
}
