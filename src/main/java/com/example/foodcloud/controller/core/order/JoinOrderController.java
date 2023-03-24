package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.controller.core.order.dto.JoinOrderControllerDto;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
import com.example.foodcloud.domain.order.menu.service.add.OrderMenuAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class JoinOrderController {
    private final OrderMenuAddService orderMenuAddService;
    private final OrderMainRepository orderMainRepository;
    private final FoodMenuRepository foodMenuRepository;


    @GetMapping("/join")
    public String get(@RequestParam Long orderMainId, Model model) {
        OrderMain orderMain = orderMainRepository.validateOrderMainNotCancel(orderMainId);
        List<FoodMenu> foodMenu = foodMenuRepository.findByRestaurantId(orderMain.getRestaurant().getId());

        model.addAttribute("orderMainInfo", orderMain);
        model.addAttribute("foodMenuList", foodMenu);
        return "thymeleaf/order/join";
    }

    @PostMapping("/join")
    public String post(@SessionAttribute Long userId,
                       @RequestParam Long orderMainId,
                       @Valid JoinOrderControllerDto joinOrderControllerDto) {

        orderMenuAddService.add(userId, joinOrderControllerDto.convert(orderMainId));

        return "thymeleaf/order/join";
    }
}
