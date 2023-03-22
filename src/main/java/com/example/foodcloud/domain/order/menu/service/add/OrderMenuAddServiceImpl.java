package com.example.foodcloud.domain.order.menu.service.add;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.update.FoodMenuUpdateService;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
import com.example.foodcloud.domain.order.menu.service.add.dto.OrderMenuAddServiceDto;
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
    private final FoodMenuUpdateService foodMenuUpdateService;
    private final OrderMenuRepository orderMenuRepository;
    private final OrderMainRepository orderMainRepository;
    private final UserRepository userRepository;
    private final FoodMenuRepository foodMenuRepository;

    @Override
    public void add(Long userId, OrderMenuAddServiceDto orderMenuAddServiceDto) {
        User user = userRepository.validateUser(userId);
        FoodMenu foodMenu = foodMenuRepository.validateFoodMenu(orderMenuAddServiceDto.getFoodMenuId());
        OrderMain orderMain = orderMainRepository.validateOrderMainNotCancel(userId, orderMenuAddServiceDto.getOrderMainId());

        OrderMenu orderMenu = new OrderMenu(
                orderMenuAddServiceDto.getLocation(),
                orderMenuAddServiceDto.getCount(),
                getTime(),
                user,
                foodMenu,
                orderMain
        );

        orderMenuRepository.save(orderMenu);
        
        foodMenuUpdateService.updateFoodMenuOrderCount(foodMenu.getId());
    }

    private String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
    }
}
