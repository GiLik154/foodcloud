package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.ordermenu.service.OrderMenuCanceler;
import com.example.foodcloud.domain.ordermenu.service.OrderMenuDeleter;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.exception.NotFoundOrderMenuException;
import com.example.foodcloud.security.login.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class OrderController {
    private final OrderMenuRepository orderMenuRepository;

    private final OrderMenuCanceler orderMenuCanceler;
    private final OrderMenuDeleter orderMenuDeleter;

    @GetMapping("/list")
    public String showListPage(Model model) {
        Long userId = getCurrentUserId();

        model.addAttribute("orderMenuList", orderMenuRepository.findByUserId(userId));
        return "thymeleaf/order/list";
    }

    @GetMapping("/cancel/{orderMenuId}")
    public String showCancelPage(@PathVariable Long orderMenuId, Model model) {
        OrderMenu orderMenu = orderMenuRepository.findByIdAndResultNot(orderMenuId, OrderResult.CANCELED).orElseThrow(NotFoundOrderMenuException::new);

        model.addAttribute("orderMenuInfo", orderMenu);
        return "thymeleaf/order/cancel";
    }

    @PutMapping("/cancel/{orderMenuId}")
    public String cancel(@RequestParam Long orderMenuId, Model model) {
        Long userId = getCurrentUserId();

        model.addAttribute("cancelMsg", orderMenuCanceler.cancel(userId, orderMenuId));

        return "thymeleaf/order/cancel-check";
    }

    @GetMapping("/delete/{orderMenuId}")
    public String showDeletePage(Long orderMenuId, Model model) {
        OrderMenu orderMenu = orderMenuRepository.findByIdAndResultNot(orderMenuId, OrderResult.CANCELED).orElseThrow(NotFoundOrderMenuException::new);

        model.addAttribute("orderMenu", orderMenu);
        return "thymeleaf/order/delete";
    }

    @DeleteMapping("/{orderMenuId}")
    public String delete(Long orderMenuId) {
        Long userId = getCurrentUserId();

        orderMenuDeleter.delete(userId, orderMenuId);

        return "thymeleaf/order/delete-check";
    }

    private Long getCurrentUserId() {
        UserDetail userDetail = getUserDetail();

        return userDetail.getUserId();
    }

    private UserDetail getUserDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (UserDetail) authentication.getPrincipal();
    }
}
