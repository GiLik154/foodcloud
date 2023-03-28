package com.example.foodcloud.domain.payment.bank.service.payment;

import com.example.foodcloud.domain.order.menu.item.domain.OrderMenuItems;
import com.example.foodcloud.domain.order.menu.item.domain.OrderMenuItemsRepository;
import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.order.menu.menu.service.update.payment.OrderMenuPaymentUpdateService;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("088")
@RequiredArgsConstructor
public class ShinHanBankPaymentServiceImpl implements PaymentService {
    private final OrderMenuPaymentUpdateService orderMenuPaymentUpdateService;
    private final BankAccountRepository bankAccountRepository;
    private final OrderMenuItemsRepository orderMenuItemsRepository;

    @Override
    public String pay(Long userId, Long orderMenuId, Long bankAccountId, int price) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, bankAccountId)) {

            orderMenuPaymentUpdateService.update(orderMenuId, getBankAccount(bankAccountId));

            return price + " price ShinHan bank payment succeed";
        }
        return "ShinHan bank payment fail";
    }

    @Override
    public String refund(Long userId, Long bankAccountId ,OrderMenuItems orderMenuItems) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, orderMenu.getBankAccount().getId())) {
            return orderMenuItems.getPrice() + " price ShinHan bank refund succeed";
        }
        return "ShinHan bank refund fail";
    }

    private BankAccount getBankAccount(Long bankAccountId) {
        return bankAccountRepository.findById(bankAccountId).orElseThrow(NotFoundBankAccountException::new);
    }
}
