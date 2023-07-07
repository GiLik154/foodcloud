package com.example.foodcloud.domain.payment.service.payments;

import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.service.OrderMenuUpdater;
import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("004")
@Transactional
@RequiredArgsConstructor
public class KBBankPayment implements PaymentService {
    private final BankAccountRepository bankAccountRepository;
    private final OrderMenuUpdater updater;

    @Override
    public String pay(Long userId, Long orderMenuId, Long paymentId, int price) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, paymentId)) {

            updater.paymentUpdate(orderMenuId, getBankAccount(paymentId));

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
