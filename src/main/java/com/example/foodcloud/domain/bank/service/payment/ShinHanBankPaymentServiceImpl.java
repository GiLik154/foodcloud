package com.example.foodcloud.domain.bank.service.payment;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.order.menu.service.update.payment.OrderMenuPaymentUpdateService;
import com.example.foodcloud.domain.point.domain.Point;
import com.example.foodcloud.enums.BankCode;
import com.example.foodcloud.enums.PaymentType;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import com.example.foodcloud.exception.NotFoundPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("088")
@RequiredArgsConstructor
public class ShinHanBankPaymentServiceImpl implements PaymentService {
    private final Map<String, OrderMenuPaymentUpdateService> updateServiceMap;
    private final BankAccountRepository bankAccountRepository;

    @Override
    public String pay(String bank, Long userId, Long orderMenuId, Long bankAccountId, int price) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, bankAccountId)) {
            OrderMenuPaymentUpdateService orderMenuPaymentUpdateService =
                    updateServiceMap.get(PaymentType.findByPaymentType(
                            BankCode.returnBankCode(bank)));

            orderMenuPaymentUpdateService.isUpdate(orderMenuId, getBankAccount(bankAccountId));

            return price + " price ShinHan bank payment succeed";
        }
        return "ShinHan bank payment fail";
    }

    @Override
    public String refund(Long userId, Long orderMenuId, Long bankAccountId, int price) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, bankAccountId)) {
            return price + " price ShinHan bank refund succeed";
        }
        return "ShinHan bank refund fail";
    }

    private BankAccount getBankAccount(Long bankAccountId) {
        return bankAccountRepository.findById(bankAccountId).orElseThrow(NotFoundBankAccountException::new);
    }
}
