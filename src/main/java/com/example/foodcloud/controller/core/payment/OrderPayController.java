package com.example.foodcloud.controller.core.payment;

import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.domain.payment.service.payments.PaymentService;
import com.example.foodcloud.enums.PaymentCode;
import com.example.foodcloud.exception.NotFoundOrderMenuException;
import com.example.foodcloud.exception.NotFoundPointException;
import com.example.foodcloud.security.login.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        OrderMenu orderMenu = orderMenuRepository.findByIdFetchJoin(orderMenuId).orElseThrow(NotFoundOrderMenuException::new);

        model.addAttribute("orderMenu", orderMenu);

        return "thymeleaf/payment/pay";
    }

    @GetMapping("/point")
    @ResponseBody
    public Point payForPoint() {
        Long userId = getCurrentUserId();

        return pointRepository.findByUserId(userId)
                .orElseThrow(NotFoundPointException::new);
    }

    @GetMapping("/bank")
    @ResponseBody
    public List<BankAccount> payForBank() {
        Long userId = getCurrentUserId();

        return bankAccountRepository.findByUserId(userId);
    }

    @PostMapping("/{orderMenuId}")
    public String post(@PathVariable Long orderMenuId,
                       PaymentCode paymentCode,
                       Long bankAccountId,
                       int price,
                       Model model) {
        Long userId = getCurrentUserId();

        PaymentService paymentService = validPayment(paymentCode);

        model.addAttribute("payment", paymentService.pay(userId, orderMenuId, bankAccountId, price));

        return "thymeleaf/payment/pay-check";
    }

    private PaymentService validPayment(PaymentCode paymentCode){
        PaymentCode.validate(paymentCode);

        return paymentServiceMap.get(paymentCode.getCode());
    }

    private Long getCurrentUserId() {
        UserDetail userDetail = getUserDetail();

        return userDetail.getUserId();
    }

    private UserDetail getUserDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (UserDetail) authentication.getPrincipal();
    }
}
