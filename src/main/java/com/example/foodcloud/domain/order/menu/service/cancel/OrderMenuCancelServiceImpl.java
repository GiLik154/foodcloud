package com.example.foodcloud.domain.order.menu.service.cancel;

import com.example.foodcloud.domain.payment.bank.service.payment.PaymentService;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.order.menu.service.update.OrderMenuResultUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuCancelServiceImpl implements OrderMenuCancelService {
    private final OrderMenuResultUpdateService orderMenuResultUpdateService;
    private final Map<String, PaymentService> paymentServiceMap;
    private final OrderMenuRepository orderMenuRepository;

    /**
     * validateOrderMenuForUserIdAndId를 사용해서 orderMenu를 가지고옴
     * 만약 orderMenu가 존재하지 않으면 NotFoundOrderMenuException 익셉션이 발생함
     * OrderMenuResultUpdateService에서 OrderMenu의 result를 업데이트 함.
     * OrderMenu에서 결제수단이 무엇인지 찾은 후에
     * PaymentService를 가지고와서 주문을 환불하는 서비스를 호출함
     * 이후 paymentService의 결과를 반환해줌
     */
    public String cancel(Long userId, Long orderMenuId) {
        OrderMenu orderMenu = orderMenuRepository.validateOrderMenuForUserIdAndId(userId, orderMenuId);

        orderMenuResultUpdateService.update(orderMenuId, "CANCELED");

        PaymentService paymentService = paymentServiceMap.get(orderMenu.getPayment().getPaymentCode().getCode());

        return paymentService.refund(userId, orderMenu);
    }
}
