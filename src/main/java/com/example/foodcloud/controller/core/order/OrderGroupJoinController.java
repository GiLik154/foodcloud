package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.controller.core.order.dto.OrderGroupJoinControllerDto;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.ordermenu.service.add.OrderMenuAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class OrderGroupJoinController {
    private final OrderMenuAddService orderMenuAddService;
    private final GroupBuyListRepository groupBuyListRepository;
    private final FoodMenuRepository foodMenuRepository;


    @GetMapping("/join")
    public String get(@RequestParam Long orderJoinGroupId, Model model) {
        GroupBuyList groupBuyList = groupBuyListRepository.findValidByIdAndNotCancel(orderJoinGroupId);
        List<FoodMenu> foodMenu = foodMenuRepository.findByRestaurantId(groupBuyList.getRestaurant().getId());

        model.addAttribute("OrderJoinGroupInfo", groupBuyList);
        model.addAttribute("foodMenuList", foodMenu);
        return "thymeleaf/order/join";
    }

    @PostMapping("/join")
    public String post(@SessionAttribute Long userId,
                       @RequestParam Long orderJoinGroupId,
                       @Valid OrderGroupJoinControllerDto orderGroupJoinControllerDto) {

        Long orderMenuId = orderMenuAddService.add(userId, orderGroupJoinControllerDto.convert(orderJoinGroupId));

        return "redirect:/payment/pay/" + orderMenuId;
    }
}
