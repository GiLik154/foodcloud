package com.example.foodcloud.domain.order.main.service.add;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
import com.example.foodcloud.domain.order.main.service.add.dto.OrderMainAddDto;
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
    private final OrderMainRepository orderMainRepository;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final RestaurantRepository restaurantRepository;

    public void add(Long userId, OrderMainAddDto orderMainAddDto) {
        User user = userRepository.validateUser(userId);
        BankAccount bankAccount = bankAccountRepository.validateBankAccount(userId, orderMainAddDto.getBankAccountId());
        Restaurant restaurant = restaurantRepository.validateRestaurant(orderMainAddDto.getRestaurantId());

        OrderMain orderMain = new OrderMain(orderMainAddDto.getLocation(),
                getTime(),
                user,
                bankAccount,
                restaurant
        );

        orderMainRepository.save(orderMain);
    }

    private String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
    }
}
