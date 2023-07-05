package com.example.foodcloud.domain.payment.service.payments;

import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.domain.ordermenu.service.update.payment.OrderMenuPaymentUpdateService;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("004")
@RequiredArgsConstructor
@Transactional
public class KBBankPayment implements PaymentService {
    private final OrderMenuPaymentUpdateService orderMenuPaymentUpdateService;
    private final BankAccountRepository bankAccountRepository;

    @Override
    public String pay(Long userId, Long orderMenuId, Long paymentId, int price) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, paymentId)) {

            orderMenuPaymentUpdateService.update(orderMenuId, getBankAccount(paymentId));

            return price + " price KB Bank payment succeed";
        }
        return "KB bank payment fail";
    }

    @Override
    public String refund(Long userId, OrderMenu orderMenu) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, orderMenu.getPayment().getId())) {
            return orderMenu.getPrice() + " price KB Bank refund succeed";
        }
        return "KB bank refund fail";
    }

    private BankAccount getBankAccount(Long bankAccountId) {
        return bankAccountRepository.findById(bankAccountId).orElseThrow(() -> new NotFoundBankAccountException("Not exist bank account"));
    }
}
