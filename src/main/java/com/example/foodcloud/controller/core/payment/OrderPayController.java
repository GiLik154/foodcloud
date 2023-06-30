package com.example.foodcloud.controller.core.payment;

import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.service.payments.PaymentService;
import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.exception.NotFoundBankCodeException;
import com.example.foodcloud.exception.NotFoundPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment/pay")
public class OrderPayController {
    private final Map<String, PaymentService> paymentServiceMap;
    private final OrderMenuRepository orderMenuRepository;
    private final PointRepository pointRepository;
    private final BankAccountRepository bankAccountRepository;

    @GetMapping("/{orderMenuId}")
    public String get(@PathVariable Long orderMenuId,
                      Model model) {
        orderMenuRepository.findById(orderMenuId).ifPresent(orderMenu ->
                model.addAttribute("orderMenu", orderMenu)
        );

        return "thymeleaf/payment/pay";
    }

    @GetMapping("/point")
    @ResponseBody
    public Point payForPoint(@SessionAttribute("userId") Long userId) {
        return pointRepository.findByUserId(userId)
                .orElseThrow(NotFoundPointException::new);
    }

    @GetMapping("/bank")
    @ResponseBody
    public List<BankAccount> payForBank(@SessionAttribute("userId") Long userId) {

        return bankAccountRepository.findByUserId(userId);
    }

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
