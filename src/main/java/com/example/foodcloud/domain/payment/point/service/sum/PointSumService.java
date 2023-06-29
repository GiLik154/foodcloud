package com.example.foodcloud.domain.payment.point.service.sum;

public interface PointSumService {
    /**
     * userId를 통해서 유저의 point가 존재하는지 확인한다.
     * updatePoin에 point를 넘겨줘서 잔액을 수정한다.
     * 동시성의 문제를 해결하기 위해 낙관적 Lock을 사용한다.
     *
     * @param userId 유저의 아이디
     * @param price  수정되야 할 가격
     */
    boolean sum(Long userId, int price);
}
