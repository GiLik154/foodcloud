package com.example.foodcloud.service.bank_account;

import com.example.foodcloud.entity.BankAccountRepository;
import com.example.foodcloud.service.bank_account.dto.BankPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("004")
@RequiredArgsConstructor
public class KBBankPaymentServiceImpl implements BankPaymentService {
    private final BankAccountRepository bankAccountRepository;

    public String payment(BankPaymentDto bankPaymentDto) {
        if (bankAccountRepository.existsBankAccountByUserIdAndAccountNumberAndBank(bankPaymentDto.getUserId(),
                bankPaymentDto.getAccountNumber(),
                bankPaymentDto.getBank())) {
            return "KB bank payment succeed";
        }
        return "KB bank payment fail";
    }
}
