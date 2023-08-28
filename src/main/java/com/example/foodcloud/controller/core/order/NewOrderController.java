package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.application.order.NewOrder;
import com.example.foodcloud.controller.core.order.dto.NewOrderCreateControllerDto;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.security.login.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/new-order")
public class NewOrderController {
    private final FoodMenuRepository foodMenuRepository;

    private final NewOrder newOrder;

    @GetMapping("")
    public String get(@RequestParam Long restaurantId, Model model) {
        model.addAttribute("foodMenuList", foodMenuRepository.findByRestaurantId(restaurantId));

        return "thymeleaf/order/new";
    }

    @PostMapping("")
    public String post(@Valid NewOrderCreateControllerDto newOrderCreateControllerDto) {
        Long userId = getCurrentUserId();

        Long orderMenuId = newOrder.order(userId, newOrderCreateControllerDto.convert());

        return "redirect:/payment/pay/" + orderMenuId;
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
