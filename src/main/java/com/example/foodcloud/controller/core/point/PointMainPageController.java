package com.example.foodcloud.controller.core.point;

import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.domain.payment.point.domain.PointRepository;
import com.example.foodcloud.domain.payment.point.service.PointRegister;
import com.example.foodcloud.exception.NotFoundPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/point/main")
public class PointMainPageController {
    private final PointRegister pointRegister;
    private final PointRepository pointRepository;

    @GetMapping("")
    public String get(@SessionAttribute Long userId, Model model) {

        Point point = pointRepository.findByUserId(userId).orElseGet(() -> {
            pointRegister.award(userId);
            return pointRepository.findByUserId(userId)
                    .orElseThrow(NotFoundPointException::new);
        });

        model.addAttribute("myPoint", point);

        return "thymeleaf/point/main";
    }
}
