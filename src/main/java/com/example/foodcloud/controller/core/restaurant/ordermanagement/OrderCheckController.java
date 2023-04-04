package com.example.foodcloud.controller.core.restaurant.ordermanagement;

import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.order.menu.service.update.OrderMenuResultUpdateService;
import com.example.foodcloud.enums.OrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 식당의 주인이 주문 결과를 확인하는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant/check")
public class OrderCheckController {
    private final OrderMenuRepository orderMenuRepository;
    private final OrderMenuResultUpdateService orderMenuResultUpdateService;


    /**
     * 우선 OrderResult의 값을 출력해줌.
     */
    @GetMapping("")
    public String get(Model model) {
        model.addAttribute("orderResult", OrderResult.values());
        return "thymeleaf/restaurant/check";
    }

    /**
     * PathVariable를 통해서 foodMenuId를 받아오고
     * PathVariable를 통해서 OrderResult를 받아옴.
     * 이 두 가지의 조건에 만족하는 OrderMenu를 List타입으로 반환해줌
     */
    @GetMapping("/{foodMenuId}/{result}")
    @ResponseBody
    public List<OrderMenu> outOrderResult(@PathVariable("foodMenuId") Long foodMenuId, @PathVariable("result") OrderResult orderResult) {

        return orderMenuRepository.findByFoodMenuIdAndResult(foodMenuId, orderResult);
    }

    /**
     * PathVariable를 통해서 orderMenuId 받아오고
     * PathVariable를 통해서 OrderResult를 받아옴.
     * orderMenuId를 통해서 orderMenu를 찾고 OrderResult로 업데이트해줌.
     */
    @PutMapping("/updateStatus/{orderMenuId}/{status}")
    @ResponseBody
    public void updateOrderStatus(@PathVariable("orderMenuId") Long orderMenuId, @PathVariable("status") String status) {
        orderMenuResultUpdateService.update(orderMenuId, status);
    }
}
