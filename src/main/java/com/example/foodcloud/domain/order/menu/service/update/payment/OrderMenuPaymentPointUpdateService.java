package com.example.foodcloud.domain.order.menu.service.update.payment;

import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.point.domain.Point;
import com.example.foodcloud.domain.point.domain.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("PaymentUpdateForPoint")
@Transactional
@RequiredArgsConstructor
public class OrderMenuPaymentPointUpdateService implements OrderMenuPaymentUpdateService {
    private final OrderMenuRepository orderMenuRepository;

    public boolean isUpdate(Long orderMenuId, Object payment) {
        Optional<OrderMenu> orderMenuOptional = orderMenuRepository.findById(orderMenuId);

        if (orderMenuOptional.isPresent()) {
            OrderMenu orderMenu = orderMenuOptional.get();

            orderMenu.updatePaymentForPoint(payment); //todo 여기 제네릭으로 바꾸는 법 찾아보기.
            return true;
        }
        return false;
    }
}
