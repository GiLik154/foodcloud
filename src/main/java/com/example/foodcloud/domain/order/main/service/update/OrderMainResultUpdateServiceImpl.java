package com.example.foodcloud.domain.order.main.service.update;

import com.example.foodcloud.domain.order.menu.service.update.OrderMenuListResultUpdateService;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMainResultUpdateServiceImpl implements OrderMainResultUpdateService {
    private final OrderMainRepository orderMainRepository;
    private final OrderMenuListResultUpdateService orderMenuListResultUpdateService;

    @Override
    public boolean update(Long userId, Long orderMainId, String result) {
        Optional<OrderMain> orderMainOptional = orderMainRepository.findByUserIdAndId(userId, orderMainId);
        if (orderMainOptional.isPresent()) {
            OrderResult orderResult = OrderResult.valueOf(result);

            orderMainOptional.get().updateResult(orderResult.getResult());

            orderMenuListResultUpdateService.update(orderMainId, result);

            return true;
        }
        return false;
    }
}
