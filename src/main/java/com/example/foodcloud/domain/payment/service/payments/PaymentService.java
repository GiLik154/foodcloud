package com.example.foodcloud.domain.payment.service.payments;

import com.example.foodcloud.domain.order.menu.domain.OrderMenu;

public interface PaymentService {
    /**
     * 유저의 ID를 통해서 Point가 존재하는지 확인한다.
     * 존재하면 Point의 잔고에서 금액을 더해 저장한다.
     * OrderMenu의 결과를 '결제완료'로 변경한다.
     *
     * @param userId        유저의 ID
     * @param orderMenuId   주문의 ID
     * @param bankAccountId 결제 수단의 ID
     * @param price         결제 금액
     * @return 결제의 성공 여부
     */
    String pay(Long userId, Long orderMenuId, Long bankAccountId, int price);

    /**
     * 유저의 ID를 통해 Point가 존재하는지 확인한다.
     * 존재하면 Point의 잔고에서 OrderMenu의 결제 금액을 더해 저장한다.
     *
     * @param userId    유저의 ID
     * @param orderMenu 결제 했던 메뉴
     * @return 환불의 성공 여부
     */
    String refund(Long userId, OrderMenu orderMenu);
}
