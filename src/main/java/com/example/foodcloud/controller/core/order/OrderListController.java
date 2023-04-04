package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * 계좌 리스트를 보여주는 컨트롤
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order-menu")
public class OrderListController {
    private final OrderMenuRepository orderMenuRepository;

    /**
     * 세션을 통해 userId를 받아옴.
     * userId로 모든 유저의 모든 orderMenu를 반환해줌
     */
    @GetMapping("/list")
    public String get(@SessionAttribute("userId") Long userId, Model model) {
        model.addAttribute("orderMenuList", orderMenuRepository.findByUserId(userId));
        return "thymeleaf/order/list";
    }
}
