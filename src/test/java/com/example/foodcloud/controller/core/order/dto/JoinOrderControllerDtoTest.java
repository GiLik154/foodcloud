package com.example.foodcloud.controller.core.order.dto;

import com.example.foodcloud.domain.order.menu.service.add.dto.OrderMenuAddServiceDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoinOrderControllerDtoTest {
    @Test
    void Post_주문_추가_Dto() {
        JoinOrderControllerDto joinOrderControllerDto = new JoinOrderControllerDto("testLocation", 5, 1L);

        assertEquals("testLocation", joinOrderControllerDto.getLocation());
        assertEquals(5, joinOrderControllerDto.getCount());
        assertEquals(1L, joinOrderControllerDto.getFoodMenuId());
    }

    @Test
    void Post_주문_추가_Dto_Convert() {
        JoinOrderControllerDto joinOrderControllerDto = new JoinOrderControllerDto("testLocation", 5, 1L);

        OrderMenuAddServiceDto orderMenuAddServiceDto = joinOrderControllerDto.convert(2L);

        assertEquals("testLocation", orderMenuAddServiceDto.getLocation());
        assertEquals(5, orderMenuAddServiceDto.getCount());
        assertEquals(1L, orderMenuAddServiceDto.getFoodMenuId());
        assertEquals(2L, orderMenuAddServiceDto.getOrderMainId());
    }
}