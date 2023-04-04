package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.order.menu.service.delete.OrderMenuDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * 주문을 삭제하는 컨트롤러
 * (DB에서 Delete)
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class DeleteOrderController {
    private final OrderMenuDeleteService orderMenuDeleteService;
    private final OrderMenuRepository orderMenuRepository;

    /**
     * 세션으로 userId를 받아야와하고
     * RequestParam를 통해 orderMenuId를 받아옴.
     * 이후 취소할 주문을 출력해줌.
     */
    @GetMapping("/delete")
    public String get(Long orderMenuId, Model model) {
        model.addAttribute("orderMenu", orderMenuRepository.validateOrderMenuNotCancel(orderMenuId));
        return "thymeleaf/order/delete";
    }

    /**
     * 세션으로 userId를 받아야와하고
     * RequestParam를 통해 orderMenuId를 받아옴.
     * 이후 userId와 orderMenuId를 통해 삭제함.
     */
    @PostMapping("/delete")
    public String post(@SessionAttribute Long userId,
                       Long orderMenuId,
                       Model model) {

        model.addAttribute("isDelete", orderMenuDeleteService.delete(userId, orderMenuId));

        return "thymeleaf/order/delete-check";
    }
}
