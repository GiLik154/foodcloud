package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.order.menu.service.cancel.OrderMenuCancelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 주문을 취소하는 컨트롤러
 * (Result만 Cancel로 바꿈)
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class CancelOrderController {
    private final OrderMenuCancelService orderMenuCancelService;
    private final OrderMenuRepository orderMenuRepository;

    /**
     * 세션으로 userId를 받아야와하고
     * RequestParam를 통해 orderMenuId를 받아옴.
     * 이후 취소할 주문을 출력해줌.
     */
    @GetMapping("/cancel")
    public String get(@RequestParam Long orderMenuId, Model model) {
        model.addAttribute("orderMenuInfo", orderMenuRepository.validateOrderMenuNotCancel(orderMenuId));
        return "thymeleaf/order/cancel";
    }

    /**
     * 세션으로 userId를 받아야와하고
     * RequestParam를 통해 orderMenuId를 받아옴.
     * 이후 userId와 orderMenuId를 통해 주문을 취소함.
     */
    @PostMapping("/cancel")
    public String post(@SessionAttribute Long userId,
                       @RequestParam Long orderMenuId,
                       Model model) {
        model.addAttribute("cancelMsg", orderMenuCancelService.cancel(userId, orderMenuId));

        return "thymeleaf/order/cancel-check";
    }
}
