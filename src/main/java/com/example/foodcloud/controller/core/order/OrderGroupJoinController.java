package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.controller.core.order.dto.OrderGroupJoinControllerDto;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroup;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroupRepository;
import com.example.foodcloud.domain.order.menu.service.add.OrderMenuAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 다른 OrderJoinGroup에 Join하는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class OrderGroupJoinController {
    private final OrderMenuAddService orderMenuAddService;
    private final OrderJoinGroupRepository orderJoinGroupRepository;
    private final FoodMenuRepository foodMenuRepository;


    /**
     * RequestParam로 OrderJoinGroupId을 받아옴
     * OrderJoinGroupId에 있는 Restaurant을 이용해서 음식 리스트를 출력해줌
     */
    @GetMapping("/join")
    public String get(@RequestParam Long orderJoinGroupId, Model model) {
        OrderJoinGroup orderJoinGroup = orderJoinGroupRepository.validateOrderJoinGroupNotCancel(orderJoinGroupId);
        List<FoodMenu> foodMenu = foodMenuRepository.findByRestaurantId(orderJoinGroup.getRestaurant().getId());

        model.addAttribute("OrderJoinGroupInfo", orderJoinGroup);
        model.addAttribute("foodMenuList", foodMenu);
        return "thymeleaf/order/join";
    }

    /**
     * 세션을 통해 userId를 받아옴
     * RequestParam로 OrderJoinGroupId을 받아옴
     * Dto를 통해 정보를 받아오고 Valid로 검증함
     * 이후 서비스단과 컨트롤단의 분리를 위해
     * Dto.convert 기능을 이요해서 Service Dto로 변환해줌.
     */
    @PostMapping("/join")
    public String post(@SessionAttribute Long userId,
                       @RequestParam Long orderJoinGroupId,
                       @Valid OrderGroupJoinControllerDto orderGroupJoinControllerDto) {

        Long orderMenuId = orderMenuAddService.add(userId, orderGroupJoinControllerDto.convert(orderJoinGroupId));

        return "redirect:/payment/pay/" + orderMenuId;
    }
}
