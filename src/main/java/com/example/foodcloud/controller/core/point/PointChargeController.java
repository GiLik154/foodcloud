package com.example.foodcloud.controller.core.point;

import com.example.foodcloud.controller.core.point.dto.PointDto;
import com.example.foodcloud.domain.point.domain.Point;
import com.example.foodcloud.domain.point.domain.PointRepository;
import com.example.foodcloud.domain.point.service.sum.PointSumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PointChargeController {
    private final PointSumService pointSumService;
    private final PointRepository pointRepository;

    @GetMapping("/point/charge")
    public String get(@SessionAttribute Long userId, Model model) {
        Optional<Point> optionalPoint = pointRepository.findByUserId(userId);
        optionalPoint.ifPresent(point -> model.addAttribute("myPoint", point));
        return "thymeleaf/point/charge";
    }

    @PostMapping("/point/charge")
    public String post(@SessionAttribute Long userId, @Valid PointDto pointDto, Model model) {
        model.addAttribute("isCharge", pointSumService.sum(userId, pointDto.getPoint()));
        return "thymeleaf/point/charge-check";
    }
}
