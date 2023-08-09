package com.example.foodcloud.controller.core.point;

import com.example.foodcloud.controller.core.point.req.PointReq;
import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.domain.payment.service.point.PointCalculator;
import com.example.foodcloud.domain.payment.service.point.PointRegister;
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
@RequiredArgsConstructor
@RequestMapping(value = "/point")
public class PointController {
    private final PointRepository pointRepository;

    private final PointRegister pointRegister;
    private final PointCalculator pointCalculator;

    @GetMapping("/main")
    public String showMainPage(Model model) {
        Long userId = getCurrentUserId();

        validUserPoint(userId);

        Point point = pointRepository.findByUserId(userId).orElseThrow(NotFoundPointException::new);

        model.addAttribute("myPoint", point);

        return "thymeleaf/point/main";
    }

    @GetMapping("/charge")
    public String showChargePage(Model model) {
        Long userId = getCurrentUserId();

        Point point = pointRepository.findByUserId(userId).orElseThrow(NotFoundPointException::new);

        model.addAttribute("myPoint", point);

        return "thymeleaf/point/charge";
    }

    @PutMapping("/charge")
    public String chargePoint(@Valid PointReq req) {
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

    private void validUserPoint(Long userId){
        if(!pointRepository.existsByUserId(userId)) pointRegister.register(userId);
    }
}
