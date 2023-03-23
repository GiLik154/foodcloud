package com.example.foodcloud.controller.core.user.dto;

import com.example.foodcloud.domain.user.service.join.dto.UserJoinServiceDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserJoinControllerDtoTest {
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
        UserJoinServiceDto userJoinServiceDto = userJoinControllerDto.convert();

        assertEquals("testName", userJoinServiceDto.getName());
        assertEquals("testPassword", userJoinServiceDto.getPassword());
        assertEquals("testPhone", userJoinServiceDto.getPhone());
    }
}