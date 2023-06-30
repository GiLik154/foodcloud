package com.example.foodcloud.domain.payment.service.point;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface PointRegister {
    /**
     * 유저의 ID를 통해 유저가 존재하는지 획인한다.
     * 존재하면 유저를 통해 Point의 객체를 생성한다.
     * JPA를 통해 DB에 save
     *
     * @param userId 유저의 아이디
     * @throws UsernameNotFoundException 유저의 ID를 통해 유저가 존재하는지
     *                                   획인할때 존재하지 않으면 발생
     */
    void register(Long userId);
}