package com.example.foodcloud.domain.order.menu.service.cancel;

import com.example.foodcloud.exception.NotFoundOrderMenuException;

public interface OrderMenuCancelService {
    /**
     * 유저의 ID와 orderMenu의 ID를 통해 orderMenu가 존재하는지 확인
     * 존재하면 OrderMenuResultUpdateService를 통해 OrderMenu의 result를 업데이트 한다.
     * OrderMenu에서 결제수단을 찾은 후에 PaymentService를 통해 주문을 환불하는 서비스를 호출한다.
     * 이후 PaymentService의 실행 결과를 반환한다
     *
     * @param userId      유저의 ID
     * @param orderMenuId orderMenu의 ID
     * @throws NotFoundOrderMenuException 유저의 ID와 orderMenu의 ID를 통해
     *                                    orderMenu가 존재하는지 확인할 때
     *                                    존재하지 않으면 발생
     */
    String cancel(Long userId, Long orderMenuId);
}
