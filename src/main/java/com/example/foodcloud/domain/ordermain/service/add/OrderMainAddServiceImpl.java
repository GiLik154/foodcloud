package com.example.foodcloud.domain.ordermain.service.add;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.ordermain.domain.OrderMain;
import com.example.foodcloud.domain.ordermain.domain.OrderMainRepository;
import com.example.foodcloud.domain.ordermain.service.dto.OrderMainDto;
import com.example.foodcloud.domain.ordermain.service.update.OrderMainResultUpdateService;
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
    private final OrderMainResultUpdateService orderMainResultUpdateService;

    public void add(Long userId, OrderMainDto orderMainId) {
        User user = userRepository.validateUser(userId);
        BankAccount bankAccount = bankAccountRepository.validateBankAccount(userId, orderMainId.getBankAccountId());
        Restaurant restaurant = restaurantRepository.validateRestaurant(userId, orderMainId.getRestaurantId());

        OrderMain orderMain = new OrderMain(orderMainId.getLocation(),
                getTime(),
                user,
                bankAccount,
                restaurant
        );

        orderMainRepository.save(orderMain);
    }

    private String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-mm-dd-HH:mm:ss"));
    }
}
