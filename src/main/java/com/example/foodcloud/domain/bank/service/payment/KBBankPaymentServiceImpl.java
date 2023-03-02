package com.example.foodcloud.domain.bank.service.payment;

import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("004")
@RequiredArgsConstructor
public class KBBankPaymentServiceImpl implements BankPaymentService {
    private final BankAccountRepository bankAccountRepository;

    @Override
    public String payment(Long userId, Long bankAccountId, int price) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId ,bankAccountId)) {
            return price + " price KB Bank payment succeed";
        }
        return "KB bank payment fail";
    }
}
