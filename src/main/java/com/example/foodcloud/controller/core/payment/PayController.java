package com.example.foodcloud.controller.core.payment;

import com.example.foodcloud.domain.payment.bank.service.payment.PaymentService;
import com.example.foodcloud.enums.PaymentCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PayController {
    private final Map<String, PaymentService> paymentServiceMap;

    @GetMapping("/pay")
    public String get() {
        return "thymeleaf/payment/pay";
    }

    @PostMapping("/pay")
    public String post(@SessionAttribute("userId") Long userId,
                       String bank,
                       Long orderMenuId,
                       Long bankAccountId,
                       int price,
                       Model model) {

        PaymentCode.valueOfCode(bank);

        //todo 여기 수정해야함
        PaymentService paymentService = paymentServiceMap.get(bank);

        paymentService.pay(userId, orderMenuId, bankAccountId, price);

        return "thymeleaf/payment/pay";
    }
}
