package com.example.foodcloud.domain.payment.service.bank;

import com.example.foodcloud.domain.payment.service.bank.commend.BankAccountRegisterCommend;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface BankAccountRegister {
    /**
     * 유저의 고유키로 유저를 조회 후
     * 커멘드를 통하여 계좌를 생성함
     *
     * @param userId                     유저의 고유키
     * @param bankAccountRegisterCommend 은행 계좌를 등록하기 위한 케맨드
     * @throws UsernameNotFoundException 유저를 찾을 수 없을 시 발생
     */
    void register(Long userId, BankAccountRegisterCommend bankAccountRegisterCommend);
}
