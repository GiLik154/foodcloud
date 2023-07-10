package com.example.foodcloud.domain.ordermenu.service;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.update.FoodMenuCountUpdateService;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.ordermenu.service.commend.OrderMenuRegisterCommend;
import com.example.foodcloud.domain.payment.domain.Payment;
import com.example.foodcloud.domain.restaurant.service.RestaurantCountUpdater;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.exception.NotFoundFoodMenuException;
import com.example.foodcloud.exception.NotFoundGroupByListException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuService implements OrderMenuRegister, OrderMenuUpdater, OrderMenuDeleter {
    private final UserRepository userRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;
    private final OrderMenuRepository orderMenuRepository;

    private final FoodMenuCountUpdateService foodMenuCountUpdateService;
    private final RestaurantCountUpdater restaurantCountUpdater;

    @Override
    public Long register(Long userId, OrderMenuRegisterCommend orderMenuRegisterCommend) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        FoodMenu foodMenu = foodMenuRepository.findById(orderMenuRegisterCommend.getFoodMenuId()).orElseThrow(NotFoundFoodMenuException::new);
        GroupBuyList groupBuyList = groupBuyListRepository.findByUserIdAndId(userId, orderMenuRegisterCommend.getOrderJoinGroupId()).orElseThrow(() ->
                new NotFoundGroupByListException("Not found GroupByList"));

        OrderMenu orderMenu = new OrderMenu(
                orderMenuRegisterCommend.getCount(),
                orderMenuRegisterCommend.getLocation(),
                user,
                groupBuyList,
                foodMenu
        );

        orderMenuRepository.save(orderMenu);

        restaurantCountUpdater.increaseOrderCount(foodMenu.getRestaurant().getId());
        foodMenuCountUpdateService.increaseOrderCount(foodMenu.getId());

        return orderMenu.getId();
    }

    @Override
    public void resultUpdate(Long orderMenuId, String result) {
        Optional<OrderMenu> orderMenuOptional = orderMenuRepository.findById(orderMenuId);

        orderMenuOptional.ifPresent(orderMenu ->
                orderMenu.updateResult(OrderResult.valueOf(result)));
    }

    @Override
    public void paymentUpdate(Long orderMenuId, Payment payment) {
        Optional<OrderMenu> orderMenuOptional = orderMenuRepository.findById(orderMenuId);

        orderMenuOptional.ifPresent(orderMenu ->
                orderMenu.completeOrderWithPayment(payment));
    }

    @Override
    public void delete(Long userId, Long orderMenuId) {
        orderMenuRepository.findByUserIdAndId(userId, orderMenuId)
                .ifPresent(orderMenuRepository::delete);
    }
}
