package com.example.foodcloud.controller.core.point;

import com.example.foodcloud.controller.core.point.req.PointReq;
import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.domain.payment.service.point.PointCalculator;
import com.example.foodcloud.exception.NotFoundPointException;
import com.example.foodcloud.security.login.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/point/charge")
@RequiredArgsConstructor
public class PointChargeController {
    private final PointCalculator pointCalculator;
    private final PointRepository pointRepository;

    @GetMapping("")
    public String get(Model model) {
        Long userId = getCurrentUserId();

        Point point = pointRepository.findByUserId(userId).orElseThrow(NotFoundPointException::new);

        model.addAttribute("myPoint", point);

        return "thymeleaf/point/charge";
    }

    @PutMapping("")
    public String post(@Valid PointReq req) {
        Long userId = getCurrentUserId();

        pointCalculator.sum(userId, req.getPoint());

        return "thymeleaf/point/charge-check";
    }

    private Long getCurrentUserId() {
        UserDetail userDetail = getUserDetail();

        return userDetail.getUserId();
    }

    private UserDetail getUserDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (UserDetail) authentication.getPrincipal();
    }
}