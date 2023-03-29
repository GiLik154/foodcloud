package com.example.foodcloud.domain.order.menu.service.update;

import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.enums.OrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuListResultUpdateServiceImpl implements OrderMenuListResultUpdateService {
    private final OrderMenuRepository orderMenuRepository;

    @Override
    public void update(Long orderMainId, String result) {
        List<OrderMenu> list = orderMenuRepository.findByOrderMainId(orderMainId);
        OrderResult orderResult = OrderResult.valueOf(result);

        for (OrderMenu orderMenu : list) {
            orderMenu.updateResult(orderResult);
        }
    }
}
