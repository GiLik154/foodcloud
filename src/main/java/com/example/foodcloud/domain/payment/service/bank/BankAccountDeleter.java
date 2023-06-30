package com.example.foodcloud.domain.payment.service.bank;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.foodcloud.exception.NotFoundBankAccountException;

public interface BankAccountDeleter {
    /**
     * 유저의 고유번호와 계좌의 고유번호가 일치하고,
     * 유저의 비밀번호가 일치하는 경우
     * 해당 계좌를 삭제할 수 있다.
     *
     * @param userId        유저의 고유번호
     * @param bankAccountId 계좌의 고유번호
     * @param password      유저의 비밀번호
     * @throws UsernameNotFoundException    유저가 존재하지 않을 시 발생
     * @throws BadCredentialsException      유저의 비밀번호가 일치하지 않을 시 발생
     * @throws NotFoundBankAccountException 계좌가 존재하지 않을 시 발생
     */
    void delete(Long userId, Long bankAccountId, String password);
}
