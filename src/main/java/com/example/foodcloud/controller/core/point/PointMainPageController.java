package com.example.foodcloud.controller.core.point;

import com.example.foodcloud.domain.point.domain.Point;
import com.example.foodcloud.domain.point.domain.PointRepository;
import com.example.foodcloud.domain.point.service.award.PointAwardService;
import com.example.foodcloud.enums.FoodType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/point/main")
public class PointMainPageController {
    private final PointAwardService pointAwardService;
    private final PointRepository pointRepository;

    @GetMapping("")
    public String get(@SessionAttribute Long userId, Model model) {
        Optional<Point> optionalPoint = pointRepository.findByUserId(userId);

        if (optionalPoint.isPresent()) {
            model.addAttribute("myPoint", optionalPoint.get());
        } else {
            pointAwardService.award(userId, 0);
            return "redirect:/point/main";
        }

        return "thymeleaf/point/main";
    }
}
