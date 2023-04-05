package com.example.foodcloud.domain.payment.bank.service.account.add;

import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.bank.service.account.add.dto.BankAccountAddServiceDto;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 계좌를 추가하는 서비스
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountAddServiceImpl implements BankAccountAddService {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    /**
     * user를 validate메소드를 통해 검증
     * (UsernameNotFoundException 익셉션 발생할 수 있음
     * 이후 BankAccout에 Dto의 정보와 user을 넣고 JPA의 save를 이용함.
     */
    @Override
    public void add(Long userId, BankAccountAddServiceDto bankAccountAddServiceDto) {
        User user = userRepository.validate(userId);

        BankAccount bankAccount = new BankAccount(user,
                bankAccountAddServiceDto.getName(),
                bankAccountAddServiceDto.getAccountNumber(),
                bankAccountAddServiceDto.getPaymentCode());

        bankAccountRepository.save(bankAccount);
    }
}