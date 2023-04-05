package com.example.foodcloud.domain.user.service.join;

import com.example.foodcloud.domain.user.service.join.dto.UserJoinServiceDto;
import com.example.foodcloud.exception.UserNameDuplicateException;

public interface UserJoinService {
    /**
     * 유저의 아이디가 DB에 저장된 것이 있는지 중복체크한다.
     * 패스워드는 bCryptPasswordEncoder로 암호화 된다.
     * 유저의 생성에 성공하면 포인트 DB도 생성이 된다.
     *
     * @param userJoinServiceDto 회원가입에 필요한 정보 (아이디, 패스워드 등)
     * @throws UserNameDuplicateException 유저의 아이디가 DB에 저장된 것이
     *                                    있는지 중복체크할 때
     *                                    중복되는 아이디가 있으면 발생
     */
    void join(UserJoinServiceDto userJoinServiceDto);
}
