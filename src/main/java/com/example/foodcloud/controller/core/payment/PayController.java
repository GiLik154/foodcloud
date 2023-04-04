package com.example.foodcloud.controller.core.payment;

import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.bank.service.payment.PaymentService;
import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.domain.payment.point.domain.PointRepository;
import com.example.foodcloud.exception.NotFoundBankCodeException;
import com.example.foodcloud.exception.NotFoundPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 결제를 처리하는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/payment/pay")
public class PayController {
    private final Map<String, PaymentService> paymentServiceMap;
    private final OrderMenuRepository orderMenuRepository;
    private final PointRepository pointRepository;
    private final BankAccountRepository bankAccountRepository;

    /**
     * PathVariable를 통해 orderMenuId를 받아옴
     * orderMenuId를 통해 orderMenu를 출력해줌
     */
    @GetMapping("/{orderMenuId}")
    public String get(@PathVariable Long orderMenuId,
                      Model model) {
        orderMenuRepository.findById(orderMenuId).ifPresent(orderMenu ->
                model.addAttribute("orderMenu", orderMenu)
        );

        return "thymeleaf/payment/pay";
    }

    /**
     * 주문에서 point 결제를 선택하면
     * 세션의 userId를 통해
     * 유저의 point 정보를 반환해줌
     */
    @GetMapping("/point")
    @ResponseBody
    public Point payForPoint(@SessionAttribute("userId") Long userId) {
        return pointRepository.findByUserId(userId)
                .orElseThrow(NotFoundPointException::new);
    }

    /**
     * 주문에서 bank 결제를 선택하면
     * 세션의 userId를 통해
     * 유저의 모든 bankAccount를 반환해줌
     */
    @GetMapping("/bank")
    @ResponseBody
    public List<BankAccount> payForBank(@SessionAttribute("userId") Long userId) {

        return bankAccountRepository.findByUserId(userId);
    }

    /**
     * PathVariable를 통해 orderMenuId를 받아오고
     * paymentCode를 받아와서
     * paymentService를 가지고 옴(Bean의 이름으로 찾아옴)
     * 잚못된 paymentCode의 경우에는 익셉션 발생
     * 이후 pay 서비스를 실행.
     */
    @PostMapping("/{orderMenuId}")
    public String post(@SessionAttribute("userId") Long userId,
                       @PathVariable Long orderMenuId,
                       String paymentCode,
                       Long bankAccountId,
                       int price,
                       Model model) {

        PaymentService paymentService = paymentServiceMap.get(paymentCode);

        if (paymentService == null) {
            throw new NotFoundBankCodeException();
        }

        model.addAttribute("payment", paymentService.pay(userId, orderMenuId, bankAccountId, price));

        return "thymeleaf/payment/pay";
    }
}
