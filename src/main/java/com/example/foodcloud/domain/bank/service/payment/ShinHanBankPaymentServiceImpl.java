package com.example.foodcloud.domain.bank.service.payment;

import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("088")
@RequiredArgsConstructor
public class ShinHanBankPaymentServiceImpl implements PaymentService {
    private final BankAccountRepository bankAccountRepository;

    @Override
    public String payment(Long userId, Long bankAccountId, int price) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, bankAccountId)) {
            return price + " price ShinHan bank payment succeed";
        }
        return "ShinHan bank payment fail";
    }
}
