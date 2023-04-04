package com.example.foodcloud.controller.core.point;

import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.domain.payment.point.domain.PointRepository;
import com.example.foodcloud.domain.payment.point.service.award.PointAwardService;
import com.example.foodcloud.exception.NotFoundPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * 유저의 포인트를 보여주는 메인 페이지 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/point/main")
public class PointMainPageController {
    private final PointAwardService pointAwardService;
    private final PointRepository pointRepository;

    /**
     * 세션을 통해 userId를 받아오고
     * optional을 통해 point db가 생성되지 않았을 경우에는
     * awrd 서비스를 통해 point 생성
     * userId를 통해서 유저의 point 정보를 띄워줌.
     */
    @GetMapping("")
    public String get(@SessionAttribute Long userId, Model model) {
        Point point = pointRepository.findByUserId(userId).orElseGet(() -> {
            pointAwardService.award(userId);
            return pointRepository.findByUserId(userId)
                    .orElseThrow(NotFoundPointException::new);
        });

        model.addAttribute("myPoint", point);

        return "thymeleaf/point/main";
    }
}
