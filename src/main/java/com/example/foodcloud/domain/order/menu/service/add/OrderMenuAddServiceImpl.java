package com.example.foodcloud.domain.order.menu.service.add;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
import com.example.foodcloud.domain.order.menu.service.dto.OrderMenuDto;
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
    private final OrderMenuRepository orderMenuRepository;
    private final OrderMainRepository orderMainRepository;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final FoodMenuRepository foodMenuRepository;

    @Override
    public void add(Long userId, OrderMenuDto orderMenuDto) {
        User user = userRepository.validateUser(userId);
        BankAccount bankAccount = bankAccountRepository.validateBankAccount(userId, orderMenuDto.getBankAccountId());
        FoodMenu foodMenu = foodMenuRepository.validateFoodMenu(orderMenuDto.getFoodMenuId());
        OrderMain orderMain = orderMainRepository.validateOrderMain(userId, orderMenuDto.getOrderMainId());

        OrderMenu orderMenu = new OrderMenu(
                orderMenuDto.getLocation(),
                orderMenuDto.getCount(),
                getTime(),
                user,
                bankAccount,
                foodMenu,
                orderMain
        );

        updateFoodMenuOrderCount(foodMenu, orderMenu);

        orderMenuRepository.save(orderMenu);
    }

    private String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-mm-dd-HH:mm:ss"));
    }

    private void updateFoodMenuOrderCount(FoodMenu foodMenu, OrderMenu orderMenu) {
        foodMenu.updateOrderMenu(orderMenu);
    }
}
