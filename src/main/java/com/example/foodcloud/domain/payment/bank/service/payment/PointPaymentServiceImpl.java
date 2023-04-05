package com.example.foodcloud.domain.payment.bank.service.payment;

import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.service.update.payment.OrderMenuPaymentUpdateService;
import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.domain.payment.point.domain.PointRepository;
import com.example.foodcloud.domain.payment.point.service.sum.PointSumService;
import com.example.foodcloud.exception.NotFoundPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 결제 서비스 (API 대신 사용)
 */
@Service("000")
@RequiredArgsConstructor
public class PointPaymentServiceImpl implements PaymentService {
    private final OrderMenuPaymentUpdateService orderMenuPaymentUpdateService;
    private final PointSumService pointSumService;
    private final PointRepository pointRepository;

    /**
     * userId를 통해 포인트의 최종 금액에서 -price를 한다.
     * bankAccountId를 받지만, 쓰지 않는 이유는 컨트롤단에서 다형성을 위해서.
     * 메소드를 따로 뺀 이유는 성공과 실패 케이스를 반화해주기 위해서
     * 이후 orderMenuPaymentUpdateService에 orderMenuId와 Point 반환해준다.
     */
    @Override
    public String pay(Long userId, Long orderMenuId, Long bankAccountId, int price) {
        if (pointSumService.sum(userId, price * -1)) {

            orderMenuPaymentUpdateService.update(orderMenuId, getPoint(userId));

            return price + " price Point payment succeed";
        }
        return "Point payment fail";
    }

    /**
     * userId와 orderMenu를 받아와서
     * orderMenu에서 Payment의 PK를 찾아와서 point의 잔고에 +price 해준다.
     * 이후 성공과 실패 String을 출력한다.
     */
    @Override
    public String refund(Long userId, OrderMenu orderMenu) {
        if (pointSumService.sum(userId, orderMenu.getPrice())) {
            return orderMenu.getPrice() + " price Point refund succeed";
        }
        return "Point refund fail";
    }

    private Point getPoint(Long userId) {
        return pointRepository.findByUserId(userId).orElseThrow(NotFoundPointException::new);
    }
}
