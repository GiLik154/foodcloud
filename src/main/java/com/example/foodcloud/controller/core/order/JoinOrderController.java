package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.controller.core.order.dto.JoinOrderControllerDto;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
import com.example.foodcloud.domain.order.menu.service.add.OrderMenuAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class JoinOrderController {
    private final OrderMenuAddService orderMenuAddService;
    private final OrderMainRepository orderMainRepository;

    @GetMapping("/join")
    public String get(Long orderMainId, Model model) {
        model.addAttribute("orderMainInfo", orderMainRepository.validateOrderMain(orderMainId));
        return "thymeleaf/order/join";
    }

    @PostMapping("/join")
    public String post(@SessionAttribute Long userId,
                       @Valid JoinOrderControllerDto joinOrderControllerDto) {

        orderMenuAddService.add(userId, joinOrderControllerDto.convert());

        return "thymeleaf/order/join";
    }
}
