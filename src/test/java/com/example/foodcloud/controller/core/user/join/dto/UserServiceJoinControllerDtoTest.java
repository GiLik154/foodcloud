package com.example.foodcloud.controller.core.user.join.dto;

import com.example.foodcloud.controller.core.user.dto.UserJoinControllerDto;
import com.example.foodcloud.domain.user.service.commend.UserJoinerCommend;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceJoinControllerDtoTest {
    @Test
    void Post_유저_회원가입_Dto() {
        UserJoinControllerDto userJoinControllerDto = new UserJoinControllerDto("testName", "testPassword", "testPhone");

        assertEquals("testName", userJoinControllerDto.getJoinName());
        assertEquals("testPassword", userJoinControllerDto.getJoinPassword());
        assertEquals("testPhone", userJoinControllerDto.getJoinPhone());
    }

    @Test
    void Post_유저_회원가입_Dto_Convert() {
        UserJoinControllerDto userJoinControllerDto = new UserJoinControllerDto("testName", "testPassword", "testPhone");
        UserJoinerCommend userJoinerCommend = userJoinControllerDto.convert();

        assertEquals("testName", userJoinerCommend.getName());
        assertEquals("testPassword", userJoinerCommend.getPassword());
        assertEquals("testPhone", userJoinerCommend.getPhone());
    }
}