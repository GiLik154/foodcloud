package com.example.foodcloud.controller.core.groupbylist;

import com.example.foodcloud.controller.core.order.dto.OrderGroupJoinControllerDto;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.ordermenu.service.OrderMenuCreator;
import com.example.foodcloud.exception.NotFoundGroupByListException;
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
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/group-by-list")
public class GroupByListController {
    private final FoodMenuRepository foodMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;

    private final OrderMenuCreator orderMenuCreator;

    @GetMapping("/join")
    public String showJoinPage(@RequestParam Long groupByListId, Model model) {
        GroupBuyList groupBuyList = groupBuyListRepository.findById(groupByListId).orElseThrow(() -> new NotFoundGroupByListException("Not found GroupByList"));

        List<FoodMenu> foodMenu = foodMenuRepository.findByRestaurantId(groupBuyList.getRestaurant().getId());

        model.addAttribute("OrderJoinGroupInfo", groupBuyList);
        model.addAttribute("foodMenuList", foodMenu);
        return "thymeleaf/order/join";
    }

    @PostMapping("/join")
    public String join(@RequestParam Long groupByListId,
                       @Valid OrderGroupJoinControllerDto orderGroupJoinControllerDto) {
        Long userId = getCurrentUserId();

        Long orderMenuId = orderMenuCreator.crate(userId, orderGroupJoinControllerDto.convert(groupByListId));

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
