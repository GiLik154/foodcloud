package com.example.foodcloud.domain.payment.bank.service.payment;

import com.example.foodcloud.domain.order.menu.item.domain.OrderMenuItems;
import com.example.foodcloud.domain.order.menu.item.domain.OrderMenuItemsRepository;
import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.order.menu.menu.service.update.payment.OrderMenuPaymentUpdateService;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("004")
@RequiredArgsConstructor
@Transactional
public class KBBankPaymentServiceImpl implements PaymentService {
    private final OrderMenuPaymentUpdateService orderMenuPaymentUpdateService;
    private final BankAccountRepository bankAccountRepository;
    private final OrderMenuItemsRepository orderMenuItemsRepository;

    @Override
    public String pay(Long userId, Long orderMenuId, Long bankAccountId, int price) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, bankAccountId)) {

            orderMenuPaymentUpdateService.update(orderMenuId, getBankAccount(bankAccountId));

            return price + " price KB Bank payment succeed";
        }
        return "KB bank payment fail";
    }

    @Override
    public String refund(Long userId, Long orderMenuId) {
        OrderMenuItems orderMenuItems = orderMenuItemsRepository.validate(orderMenuId);

        if (pointSumService.sum(userId, orderMenuItems.getPrice())) {
            return orderMenuItems.getPrice() + " price Point refund succeed";
        }
        return "KB bank refund fail";
    }

    private BankAccount getBankAccount(Long bankAccountId) {
        return bankAccountRepository.findById(bankAccountId).orElseThrow(NotFoundBankAccountException::new);
    }
}
