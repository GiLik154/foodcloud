package com.example.foodcloud.controller.core.user.req;

import com.example.foodcloud.domain.user.service.commend.UserJoinerCommend;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserJoinReqTest {
    @Test
    void 최상의_생성() {
        UserJoinReq userJoinReq = new UserJoinReq("testName", "testPassword", "testPhone");

        Assertions.assertEquals("testName", userJoinReq.getJoinName());
        Assertions.assertEquals("testPassword", userJoinReq.getJoinPassword());
        Assertions.assertEquals("testPhone", userJoinReq.getJoinPhone());
    }

    @Test
    void Convert_메소드를_실행하면_Commend로_변환됨() {
        UserJoinReq userJoinReq = new UserJoinReq("testName", "testPassword", "testPhone");
        UserJoinerCommend userJoinerCommend = userJoinReq.convert();

        assertEquals("testName", userJoinerCommend.getUsername());
        assertEquals("testPassword", userJoinerCommend.getPassword());
        assertEquals("testPhone", userJoinerCommend.getPhone());
    }
}