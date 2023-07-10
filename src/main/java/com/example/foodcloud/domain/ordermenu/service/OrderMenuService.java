package com.example.foodcloud.domain.ordermenu.service;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.FoodMenuUpdater;
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
public class OrderMenuService implements OrderMenuCreator, OrderMenuUpdater, OrderMenuDeleter {
    private final UserRepository userRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;
    private final OrderMenuRepository orderMenuRepository;

    private final FoodMenuUpdater foodMenuUpdater;
    private final RestaurantCountUpdater restaurantCountUpdater;

    @Override
    public Long crate(Long userId, OrderMenuRegisterCommend commend) {
        Long foodMenuId = commend.getFoodMenuId();
        Long orderJoinGroupId = commend.getOrderJoinGroupId();

        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        FoodMenu foodMenu = foodMenuRepository.findById(foodMenuId).orElseThrow(() -> new NotFoundFoodMenuException("Not found food menu"));
        GroupBuyList groupBuyList = groupBuyListRepository.findByUserIdAndId(userId, orderJoinGroupId).orElseThrow(() ->
                new NotFoundGroupByListException("Not found GroupByList"));

        OrderMenu orderMenu = new OrderMenu(commend.getCount(), commend.getLocation(), user, groupBuyList, foodMenu);

        orderMenuRepository.save(orderMenu);

        restaurantCountUpdater.increaseOrderCount(foodMenu.getRestaurant().getId());
        foodMenuUpdater.increaseOrderCount(foodMenu.getId());

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
