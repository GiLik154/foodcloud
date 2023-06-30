package com.example.foodcloud.controller.core.point;

import com.example.foodcloud.controller.core.point.dto.PointDto;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.domain.payment.service.point.PointCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;

@Controller
@RequestMapping("/point/charge")
@RequiredArgsConstructor
public class PointChargeController {
    private final PointCalculator pointCalculator;
    private final PointRepository pointRepository;

    @GetMapping("")
    public String get(@SessionAttribute Long userId, Model model) {
        pointRepository.findByUserId(userId).ifPresent(point ->
                model.addAttribute("myPoint", point));
        return "thymeleaf/point/charge";
    }

    @PostMapping("")
    public String post(@SessionAttribute Long userId, @Valid PointDto pointDto) {
        pointCalculator.sum(userId, pointDto.getPoint());
        return "thymeleaf/point/charge-check";
    }
}
