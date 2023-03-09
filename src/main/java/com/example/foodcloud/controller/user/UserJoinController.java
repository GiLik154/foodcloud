package com.example.foodcloud.controller.user;

import com.example.foodcloud.controller.user.dto.UserJoinControllerDto;
import com.example.foodcloud.domain.user.service.join.UserJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/join")
public class UserJoinController {
    private final UserJoinService userJoinService;

    @GetMapping("")
    public String join() {
        return "thymeleaf/user/login";
    }

    @PostMapping("")
    public String check(@Valid UserJoinControllerDto userJoinControllerDto) {

        userJoinService.join(userJoinControllerDto.convertDto());

        return "thymeleaf/user/login";
    }
}
