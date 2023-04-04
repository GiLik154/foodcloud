package com.example.foodcloud.controller.core.point;

import com.example.foodcloud.controller.core.point.dto.PointDto;
import com.example.foodcloud.domain.payment.point.domain.PointRepository;
import com.example.foodcloud.domain.payment.point.service.sum.PointSumService;
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
    private final PointSumService pointSumService;
    private final PointRepository pointRepository;

    /**
     * 세션을 통해 userId를 받아오고
     * userId를 통해서 유저의 point 정보를 띄워줌.
     */
    @GetMapping("")
    public String get(@SessionAttribute Long userId, Model model) {
        pointRepository.findByUserId(userId).ifPresent(point ->
                model.addAttribute("myPoint", point));
        return "thymeleaf/point/charge";
    }

    /**
     * PathVariable를 통해 orderMenuId를 받아오고
     * PointDto를 통해 point의 충전 금액을 받아옴
     * Valid를 통해 범위를 지정하고, 그 안의 범위만 설정할 수 있도록 함
     * 이후 pointSum 서비스를 실행함.
     */
    @PostMapping("")
    public String post(@SessionAttribute Long userId, @Valid PointDto pointDto, Model model) {
        model.addAttribute("isCharge", pointSumService.sum(userId, pointDto.getPoint()));
        return "thymeleaf/point/charge-check";
    }
}
