package com.example.foodcloud.domain.order.main.service.add;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
import com.example.foodcloud.domain.order.main.service.add.dto.OrderMainAddServiceDto;
import com.example.foodcloud.domain.order.menu.service.add.OrderMenuAddService;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
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
public class OrderMainAddServiceImpl implements OrderMainAddService {
    private final OrderMenuAddService orderMenuAddService;
    private final OrderMainRepository orderMainRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public Long add(Long userId, OrderMainAddServiceDto orderMainAddServiceDto) {
        User user = userRepository.validateUser(userId);
        Restaurant restaurant = restaurantRepository.validateRestaurant(orderMainAddServiceDto.getRestaurantId());

        OrderMain orderMain = new OrderMain(orderMainAddServiceDto.getLocation(),
                getTime(),
                user,
                restaurant
        );

        orderMainRepository.save(orderMain);

        return orderMain.getId();
    }

    private String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
    }
}
