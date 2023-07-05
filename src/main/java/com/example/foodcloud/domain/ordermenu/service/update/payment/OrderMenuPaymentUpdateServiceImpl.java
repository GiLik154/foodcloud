package com.example.foodcloud.domain.ordermenu.service.update.payment;

import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * OrderMenu의 결제수단을 업데이트 해주는 클래스
 */
@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuPaymentUpdateServiceImpl implements OrderMenuPaymentUpdateService {
    private final OrderMenuRepository orderMenuRepository;

    /**
     * Payment는 부모 객체로, 자식 객체인 (bank, poin 등의 정보가 들어올 수 있다.)
     * OrderMenuId를 통해 OrderMenu를 Optional로 가지고 오고
     * ifPresnt를 통해서 로직을 실행한다.
     * orderMenu.updatePayment를 통해 payment 를 업데이트 한다.
     */
    public void update(Long orderMenuId, Payment payment) {
        Optional<OrderMenu> orderMenuOptional = orderMenuRepository.findById(orderMenuId);

        orderMenuOptional.ifPresent(orderMenu ->
                orderMenu.completeOrderWithPayment(payment));
    }
}
