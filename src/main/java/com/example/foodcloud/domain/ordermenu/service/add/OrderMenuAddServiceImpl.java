package com.example.foodcloud.domain.ordermenu.service.add;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.update.FoodMenuCountUpdateService;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.ordermenu.service.add.dto.OrderMenuAddServiceDto;
import com.example.foodcloud.domain.restaurant.service.RestaurantCountUpdater;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuAddServiceImpl implements OrderMenuAddService {
    private final FoodMenuCountUpdateService foodMenuCountUpdateService;
    private final RestaurantCountUpdater restaurantCountUpdater;
    private final OrderMenuRepository orderMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;
    private final UserRepository userRepository;
    private final FoodMenuRepository foodMenuRepository;

    @Override
    public Long add(Long userId, OrderMenuAddServiceDto orderMenuAddServiceDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        FoodMenu foodMenu = foodMenuRepository.getValidById(orderMenuAddServiceDto.getFoodMenuId());
        GroupBuyList groupBuyList = groupBuyListRepository.findValidByUserIdAndIdAndNotCancel(userId, orderMenuAddServiceDto.getOrderJoinGroupId());

        OrderMenu orderMenu = new OrderMenu(
                orderMenuAddServiceDto.getLocation(),
                orderMenuAddServiceDto.getCount(),
                getTime(),
                user,
                foodMenu,
                groupBuyList
        );

        orderMenuRepository.save(orderMenu);

        restaurantCountUpdater.increaseOrderCount(foodMenu.getRestaurant().getId());
        foodMenuCountUpdateService.increaseOrderCount(foodMenu.getId());

        return orderMenu.getId();
    }
    
    private String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
    }
}
