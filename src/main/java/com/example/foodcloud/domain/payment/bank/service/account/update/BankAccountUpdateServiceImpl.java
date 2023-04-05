package com.example.foodcloud.domain.payment.bank.service.account.update;

import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.bank.service.account.update.dto.BankAccountUpdateServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * BankAccount를 수정하는 서비스
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountUpdateServiceImpl implements BankAccountUpdateService {
    private final BankAccountRepository bankAccountRepository;

    /**
     * userId와  bankAccountId로 BankAccount를 찾은 후
     * dto의 정보들로 업데이트 하는 메소드를 실행함.
     */
    @Override
    public void update(Long userId, Long bankAccountId, BankAccountUpdateServiceDto bankAccountUpdateServiceDto) {
        bankAccountRepository.findByUserIdAndId(userId, bankAccountId).ifPresent(bankAccount ->
                bankAccount.update(bankAccountUpdateServiceDto.getName(),
                        bankAccountUpdateServiceDto.getAccountNumber(),
                        bankAccountUpdateServiceDto.getPaymentCode()));
    }
}