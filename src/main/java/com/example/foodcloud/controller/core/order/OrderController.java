package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.controller.core.order.req.OrderJoinReq;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.ordermenu.service.OrderMenuCanceler;
import com.example.foodcloud.domain.ordermenu.service.OrderMenuCreator;
import com.example.foodcloud.domain.ordermenu.service.OrderMenuDeleter;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.exception.NotFoundGroupByListException;
import com.example.foodcloud.exception.NotFoundOrderMenuException;
import com.example.foodcloud.security.login.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class OrderController {
    private final OrderMenuRepository orderMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;
    private final FoodMenuRepository foodMenuRepository;

    private final OrderMenuCanceler orderMenuCanceler;
    private final OrderMenuDeleter orderMenuDeleter;
    private final OrderMenuCreator orderMenuCreator;

    @GetMapping("/join/{groupByListId}")
    public String showJoinPage(@PathVariable Long groupByListId,
                               Model model) {
        GroupBuyList groupBuyList = groupBuyListRepository.findById(groupByListId).orElseThrow(() ->
                new NotFoundGroupByListException("Not found GroupByList"));

        List<FoodMenu> foodMenu = foodMenuRepository.findByRestaurantId(groupBuyList.getRestaurant().getId());

        model.addAttribute("GroupBuyListInfo", groupBuyList);
        model.addAttribute("foodMenuList", foodMenu);
        return "thymeleaf/order/join";
    }

    @PostMapping("/join/{groupByListId}")
    public String create(@PathVariable Long groupByListId,
                         @Valid OrderJoinReq req) {
        Long userId = getCurrentUserId();

        Long orderMenuId = orderMenuCreator.crate(userId, req.convert(groupByListId));

        return "redirect:/payment/pay/" + orderMenuId;
    }

    @GetMapping("/list")
    public String showListPage(Model model) {
        Long userId = getCurrentUserId();

        model.addAttribute("orderMenuList", orderMenuRepository.findByUserIdFetchJoin(userId));
        return "thymeleaf/order/list";
    }

    @GetMapping("/cancel/{orderMenuId}")
    public String showCancelPage(@PathVariable Long orderMenuId, Model model) {
        OrderMenu orderMenu = orderMenuRepository.findByIdAndResultNotFetchJoin(orderMenuId, OrderResult.CANCELED).orElseThrow(NotFoundOrderMenuException::new);

        model.addAttribute("orderMenu", orderMenu);
        return "thymeleaf/order/cancel";
    }

    @PutMapping("/cancel/{orderMenuId}")
    public String cancel(@PathVariable Long orderMenuId, Model model) {
        Long userId = getCurrentUserId();

        model.addAttribute("cancelMsg", orderMenuCanceler.cancel(userId, orderMenuId));

        return "thymeleaf/order/cancel-check";
    }

    @GetMapping("/delete/{orderMenuId}")
    public String showDeletePage(@PathVariable Long orderMenuId, Model model) {
        OrderMenu orderMenu = orderMenuRepository.findByIdAndResultNotFetchJoin(orderMenuId, OrderResult.CANCELED).orElseThrow(NotFoundOrderMenuException::new);

        model.addAttribute("orderMenu", orderMenu);
        return "thymeleaf/order/delete";
    }

    @DeleteMapping("/{orderMenuId}")
    public String delete(@PathVariable Long orderMenuId) {
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
