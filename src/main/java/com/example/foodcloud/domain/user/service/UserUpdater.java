package com.example.foodcloud.domain.user.service;

public interface UserUpdater {
    /**
     * 유저의 ID로 해당 유저가 존재하는지 확인한다.
     * 존재하는 경우 휴대폰 번호를 변경한다.
     *
     * @param userId 유저의 아이디
     * @param phone  유저의 휴대폰 번호
     */
    void update(Long userId, String phone);
}