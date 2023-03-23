package com.example.foodcloud.controller.core.point.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointDtoTest {

    @Test
    void 포인트_컨트롤_Dto() {
        PointDto pointDto = new PointDto(5000);

        assertEquals(5000, pointDto.getPoint());
    }
}