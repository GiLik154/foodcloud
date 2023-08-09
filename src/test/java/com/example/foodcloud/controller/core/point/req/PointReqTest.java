package com.example.foodcloud.controller.core.point.req;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointReqTest {

    @Test
    void 포인트_컨트롤_Dto() {
        PointReq pointReq = new PointReq(5000);

        assertEquals(5000, pointReq.getPoint());
    }
}