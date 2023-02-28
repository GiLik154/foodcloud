package com.example.foodcloud.service.bank_account;

import com.example.foodcloud.entity.BankAccountRepository;
import com.example.foodcloud.service.bank_account.dto.BankPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("088")
@RequiredArgsConstructor
public class ShinHanBankPaymentServiceImpl implements BankPaymentService {
    private final BankAccountRepository bankAccountRepository;

    public String payment(BankPaymentDto bankPaymentDto) {
        if (bankAccountRepository.existsBankAccountByUserIdAndAccountNumberAndBank(bankPaymentDto.getUserId(),
                bankPaymentDto.getAccountNumber(),
                bankPaymentDto.getBank())) {
            return "ShinHan bank payment succeed";
        }
        return "ShinHan bank payment fail";
    }
}
