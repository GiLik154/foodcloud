package com.example.foodcloud.domain.order.menu.service.update;

import com.example.foodcloud.domain.order.OrderResult;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
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
    public boolean update(Long userId, Long orderMenuId, String result) {
        Optional<OrderMenu> orderMenuOptional = orderMenuRepository.findByUserIdAndId(userId, orderMenuId);

        if (orderMenuOptional.isPresent()) {
            OrderResult orderResult = OrderResult.valueOf(result);

            OrderMenu orderMenu = orderMenuOptional.get();

            orderMenu.updateResult(orderResult.getResult());

            return true;
        }
        return false;
    }
}
