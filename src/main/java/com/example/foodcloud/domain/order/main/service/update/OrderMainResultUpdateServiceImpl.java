package com.example.foodcloud.domain.order.main.service.update;

import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMainResultUpdateServiceImpl implements OrderMainResultUpdateService {
    private final OrderMainRepository orderMainRepository;
    private final OrderMenuRepository orderMenuRepository;

    @Override
    public boolean update(Long userId, Long orderMainId, String result) {
        Optional<OrderMain> orderMainOptional = orderMainRepository.findByUserIdAndId(userId, orderMainId);
        if (orderMainOptional.isPresent()) {
            OrderResult orderResult = OrderResult.valueOf(result);

            orderMainOptional.get().updateResult(orderResult.getResult());

            updateOrderMenu(orderMainId, result);

            return true;
        }
        return false;
    }

    private void updateOrderMenu(Long orderMainId, String result) {
        List<OrderMenu> list = orderMenuRepository.findByOrderMainId(orderMainId);

        OrderResult orderResult = OrderResult.valueOf(result);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).updateResult(orderResult.getResult());
        }

    }
}
