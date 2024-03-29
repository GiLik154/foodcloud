package com.example.foodcloud.domain.payment.service.payments;

import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.service.OrderMenuUpdater;
import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("088")
@RequiredArgsConstructor
public class ShinHanBankPayment implements PaymentService {
    private final OrderMenuUpdater updater;
    private final BankAccountRepository bankAccountRepository;

    @Override
    public String pay(Long userId, Long orderMenuId, Long paymentId, int price) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, paymentId)) {

            updater.paymentUpdate(orderMenuId, getBankAccount(paymentId));

            return price + " price ShinHan bank payment succeed";
        }
        return "ShinHan bank payment fail";
    }

    @Override
    public String refund(Long userId, OrderMenu orderMenu) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, orderMenu.getPayment().getId())) {
            return orderMenu.getPrice() + " price ShinHan bank refund succeed";
        }
        return "ShinHan bank refund fail";
    }

    private BankAccount getBankAccount(Long bankAccountId) {
        return bankAccountRepository.findById(bankAccountId).orElseThrow(() -> new NotFoundBankAccountException("Not exist bank account"));
    }
}
