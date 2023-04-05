package com.example.foodcloud.domain.order.menu.service.add;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.update.FoodMenuCountUpdateService;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroup;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroupRepository;
import com.example.foodcloud.domain.order.menu.service.add.dto.OrderMenuAddServiceDto;
import com.example.foodcloud.domain.restaurant.service.update.RestaurantCountUpdateService;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuAddServiceImpl implements OrderMenuAddService {
    private final FoodMenuCountUpdateService foodMenuCountUpdateService;
    private final RestaurantCountUpdateService restaurantCountUpdateService;
    private final OrderMenuRepository orderMenuRepository;
    private final OrderJoinGroupRepository orderJoinGroupRepository;
    private final UserRepository userRepository;
    private final FoodMenuRepository foodMenuRepository;

    /**
     * User를 validate메소드로 검증해서 받아옴 (유저가 없을 경우 UsernameNotFoundException 익셉션 발생함)
     * FoodMenu를 validate메소드로 검증해서 받아옴 (음식이 없을 경우  NotFoundFoodMenuException 익셉션 발생함)
     * OrderJoinGroup를 validateOrderJoinGroupNotCancel로 가져오는데, 결과가 Cancel이 아닌 것만 가지고 옴
     * (Order가 없을 경우 NotFoundOrderJoinGroupException 익셉션 발생함)
     * 이후 dto를 통해 OrderMenu를 생성 후 저장함
     * 식당과 음식의 주문 횟수를 1씩 증가시킴
     */
    @Override
    public Long add(Long userId, OrderMenuAddServiceDto orderMenuAddServiceDto) {
        User user = userRepository.validate(userId);
        FoodMenu foodMenu = foodMenuRepository.validate(orderMenuAddServiceDto.getFoodMenuId());
        OrderJoinGroup orderJoinGroup = orderJoinGroupRepository.validateOrderJoinGroupNotCancel(userId, orderMenuAddServiceDto.getOrderJoinGroupId());

        OrderMenu orderMenu = new OrderMenu(
                orderMenuAddServiceDto.getLocation(),
                orderMenuAddServiceDto.getCount(),
                getTime(),
                user,
                foodMenu,
                orderJoinGroup
        );

        orderMenuRepository.save(orderMenu);

        restaurantCountUpdateService.increaseOrderCount(foodMenu.getRestaurant().getId());
        foodMenuCountUpdateService.increaseOrderCount(foodMenu.getId());

        return orderMenu.getId();
    }

    /**
     * 주문 날짜를 반환해줌
     */
    private String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
    }
}
