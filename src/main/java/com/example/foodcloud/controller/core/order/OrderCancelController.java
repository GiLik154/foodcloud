package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.ordermenu.service.OrderMenuCanceler;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.exception.NotFoundOrderMenuException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class OrderCancelController {
    private final OrderMenuCanceler orderMenuCanceler;
    private final OrderMenuRepository orderMenuRepository;

    @GetMapping("/cancel")
    public String get(@RequestParam Long orderMenuId, Model model) {
        OrderMenu orderMenu = orderMenuRepository.findByIdAndResultNot(orderMenuId, OrderResult.CANCELED).orElseThrow(NotFoundOrderMenuException::new);

        model.addAttribute("orderMenuInfo", orderMenu);
        return "thymeleaf/order/cancel";
    }

    @PostMapping("/cancel")
    public String post(@SessionAttribute Long userId,
                       @RequestParam Long orderMenuId,
                       Model model) {
        model.addAttribute("cancelMsg", orderMenuCanceler.cancel(userId, orderMenuId));

        return "thymeleaf/order/cancel-check";
    }
}
