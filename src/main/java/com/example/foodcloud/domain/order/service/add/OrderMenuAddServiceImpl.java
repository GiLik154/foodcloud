package com.example.foodcloud.domain.order.service.add;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.domain.OrderMenu;
import com.example.foodcloud.domain.order.service.dto.OrderMenuDto;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuAddServiceImpl implements OrderMenuAddService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final BankAccountRepository bankAccountRepository;
    private final FoodMenuRepository foodMenuRepository;

    public void add(Long userId, OrderMenuDto orderMenuDto) {
        User user = userRepository.validateUser(userId);
        BankAccount bankAccount = bankAccountRepository.validateBankAccount(userId, orderMenuDto.getBankAccountId());
        Restaurant restaurant = restaurantRepository.validateRestaurant(userId, orderMenuDto.getRestaurantId());
        FoodMenu foodMenu = foodMenuRepository.validateFoodMenu(userId, orderMenuDto.getFoodMenuId());

//        OrderMenu orderMenu = new OrderMenu(orderMenuDto.getLocation(),
//
//
//                )


    }

    private String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-mm-dd-HH:mm:ss"));
    }
}
