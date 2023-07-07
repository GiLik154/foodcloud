package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.ordermenu.service.OrderMenuDeleter;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.exception.NotFoundOrderMenuException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class OrderDeleteController {
    private final OrderMenuDeleter orderMenuDeleter;
    private final OrderMenuRepository orderMenuRepository;

    @GetMapping("/delete")
    public String get(Long orderMenuId, Model model) {
        OrderMenu orderMenu = orderMenuRepository.findByIdAndResultNot(orderMenuId, OrderResult.CANCELED).orElseThrow(NotFoundOrderMenuException::new);

        model.addAttribute("orderMenu", orderMenu);
        return "thymeleaf/order/delete";
    }

    @PostMapping("/delete")
    public String post(@SessionAttribute Long userId,
                       Long orderMenuId) {

        orderMenuDeleter.delete(userId, orderMenuId);

        return "thymeleaf/order/delete-check";
    }
}
