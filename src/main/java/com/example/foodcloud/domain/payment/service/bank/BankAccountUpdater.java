package com.example.foodcloud.domain.payment.service.bank;

import com.example.foodcloud.domain.payment.service.bank.commend.BankAccountUpdaterCommend;
import com.example.foodcloud.exception.NotFoundBankAccountException;

public interface BankAccountUpdater {
    /**
     * 유저의 고유번호와
     * 계좌의 고유번호로 계좌를 찾은 후 존재하면
     * 케맨드에 따라 계좌를 업데이트함
     *
     * @param userId                    유저의 고유번호
     * @param bankAccountId             계좌의 고유번호
     * @param bankAccountUpdaterCommend 계좌를 업데이트 하기 위한 커맨드
     * @throws NotFoundBankAccountException 계좌가 존재하지 않을 시 발생
     */
    void update(Long userId, Long bankAccountId, BankAccountUpdaterCommend bankAccountUpdaterCommend);
}