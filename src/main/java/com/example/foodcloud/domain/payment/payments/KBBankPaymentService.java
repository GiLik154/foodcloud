package com.example.foodcloud.domain.payment.payments;

import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.order.menu.service.update.payment.OrderMenuPaymentUpdateService;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("004")
@RequiredArgsConstructor
@Transactional
public class KBBankPaymentService implements PaymentService {
    private final OrderMenuPaymentUpdateService orderMenuPaymentUpdateService;
    private final BankAccountRepository bankAccountRepository;

    @Override
    public String pay(Long userId, Long orderMenuId, Long bankAccountId, int price) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, bankAccountId)) {

            orderMenuPaymentUpdateService.update(orderMenuId, getBankAccount(bankAccountId));

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
