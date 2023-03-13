package com.example.foodcloud.domain.bank.service.payment;

import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("011")
@RequiredArgsConstructor
public class NHBankPaymentServiceImpl implements PaymentService {
    private final BankAccountRepository bankAccountRepository;

    @Override
    public String payment(Long userId, Long bankAccountId, int price) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, bankAccountId)) {
            return price + " price NH bank payment succeed";
        }
        return "NH bank payment fail";
    }
}
