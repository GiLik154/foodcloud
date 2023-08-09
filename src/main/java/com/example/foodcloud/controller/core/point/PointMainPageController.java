package com.example.foodcloud.controller.core.point;

import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.domain.payment.service.point.PointRegister;
import com.example.foodcloud.exception.NotFoundPointException;
import com.example.foodcloud.security.login.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/point/main")
public class PointMainPageController {
    private final PointRegister pointRegister;
    private final PointRepository pointRepository;

    @GetMapping("")
    public String get(Model model) {
        Long userId = getCurrentUserId();

        validUserPoint(userId);

        Point point = pointRepository.findByUserId(userId).orElseThrow(NotFoundPointException::new);

        model.addAttribute("myPoint", point);

        return "thymeleaf/point/main";
    }

    private Long getCurrentUserId() {
        UserDetail userDetail = getUserDetail();

        return userDetail.getUserId();
    }

    private UserDetail getUserDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (UserDetail) authentication.getPrincipal();
    }

    private void validUserPoint(Long userId){
        if(!pointRepository.existsByUserId(userId)) pointRegister.register(userId);
    }
}
