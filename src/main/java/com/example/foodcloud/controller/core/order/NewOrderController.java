package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.controller.core.order.dto.NewOrderControllerDto;
import com.example.foodcloud.domain.order.main.service.add.NewOrderService;
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
public class NewOrderController {
    private final NewOrderService newOrderService;

    @GetMapping("/new")
    public String get() {
        return "thymeleaf/order/new";
    }

    @PostMapping("/new")
    public String post(@SessionAttribute Long userId,
                       @Valid NewOrderControllerDto newOrderControllerDto,
                       Model model) {
        newOrderService.order(userId, newOrderControllerDto.convert());

        return "thymeleaf/order/new";
    }
}
