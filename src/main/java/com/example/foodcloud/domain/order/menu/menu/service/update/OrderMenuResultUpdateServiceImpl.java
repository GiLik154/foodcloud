package com.example.foodcloud.domain.order.menu.menu.service.update;

import com.example.foodcloud.domain.order.menu.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.menu.domain.OrderMenuRepository;
import com.example.foodcloud.enums.OrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuResultUpdateServiceImpl implements OrderMenuResultUpdateService {

    private final OrderMenuRepository orderMenuRepository;

    @Override
    public boolean update(Long orderMenuId, String result) {
        Optional<OrderMenu> orderMenuOptional = orderMenuRepository.findById(orderMenuId);

        if (orderMenuOptional.isPresent()) {
            OrderResult orderResult = OrderResult.valueOf(result);

            OrderMenu orderMenu = orderMenuOptional.get();

            orderMenu.updateResult(orderResult);

            return true;
        }
        return false;
    }
}
