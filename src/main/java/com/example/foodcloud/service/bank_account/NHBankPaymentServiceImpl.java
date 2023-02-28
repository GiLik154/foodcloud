package com.example.foodcloud.service.bank_account;

import com.example.foodcloud.entity.BankAccountRepository;
import com.example.foodcloud.service.bank_account.dto.BankPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("011")
@RequiredArgsConstructor
public class NHBankPaymentServiceImpl implements BankPaymentService {
    private final BankAccountRepository bankAccountRepository;

    public String payment(BankPaymentDto bankPaymentDto) {
        if (bankAccountRepository.existsBankAccountByUserIdAndAccountNumberAndBank(bankPaymentDto.getUserId(),
                bankPaymentDto.getAccountNumber(),
                bankPaymentDto.getBank())) {
            return "NH bank payment succeed";
        }
        return "NH bank payment fail";
    }
}
