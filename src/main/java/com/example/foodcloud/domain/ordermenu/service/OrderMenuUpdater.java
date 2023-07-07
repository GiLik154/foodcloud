package com.example.foodcloud.domain.ordermenu.service;

import com.example.foodcloud.domain.payment.domain.Payment;

public interface OrderMenuUpdater {

    /**
     * 오더 메뉴의 ID로 오더 메인이 존재하는지 확인한다.
     * 존재하면 매개변수로 받은 result로 오더 메인의 result를 업데이트한다.
     *
     * @param orderMenuId 오더 메뉴의 아이디
     * @param result      수정 될 result값
     */
    void resultUpdate(Long orderMenuId, String result);

    void paymentUpdate(Long orderMenuId, Payment payment);
}
