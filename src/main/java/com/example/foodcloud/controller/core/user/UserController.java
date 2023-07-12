package com.example.foodcloud.controller.core.user;

import com.example.foodcloud.controller.core.user.req.UserJoinReq;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.user.service.UserDeleter;
import com.example.foodcloud.domain.user.service.UserRegister;
import com.example.foodcloud.domain.user.service.UserUpdater;
import com.example.foodcloud.security.login.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserRegister userRegister;
    private final UserDeleter userDeleter;
    private final UserUpdater userUpdater;

    @GetMapping("/login")
    public String login() {
        return "thymeleaf/user/login";
    }

    @GetMapping("/join")
    public String join() {
        return "thymeleaf/user/login";
    }

    @PostMapping("")
    public String join(@Valid UserJoinReq req) {
        userRegister.register(req.convert());

        return "thymeleaf/user/join-check";
    }

    @GetMapping("/my-page")
    public String myPage(Model model) {
        model.addAttribute("user", findUser());

        return "thymeleaf/user/my-page";
    }

    @GetMapping("/update")
    public String update(Model model) {
        model.addAttribute("userInfo", findUser());

        return "thymeleaf/user/update";
    }

    @PutMapping("")
    public String update(String phone) {
        UserDetail userDetail = getUserDetail();

        userUpdater.update(userDetail.getUsername(), phone);

        return "redirect:/user/my-page";
    }

    @GetMapping("/delete")
    public String delete(Model model) {
        model.addAttribute("userInfo", findUser());

        return "thymeleaf/user/delete";
    }

    @DeleteMapping("/delete")
    public String delete(String name, String password) {
        userDeleter.delete(name, password);

        return "redirect:/user/my-page";
    }

    private User findUser(){
        UserDetail userDetail = getUserDetail();

        return userRepository.findByName(userDetail.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private UserDetail getUserDetail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  (UserDetail) authentication.getPrincipal();
    }
}
