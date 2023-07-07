package com.example.foodcloud.controller.core.order.join.dto;

import com.example.foodcloud.controller.core.order.dto.OrderGroupJoinControllerDto;
import com.example.foodcloud.domain.ordermenu.service.commend.OrderMenuRegisterCommend;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderGroupJoinControllerDtoTest {
    @Test
    void Post_주문_추가_Dto() {
        OrderGroupJoinControllerDto orderGroupJoinControllerDto = new OrderGroupJoinControllerDto("testLocation", 5, 1L);

        assertEquals("testLocation", orderGroupJoinControllerDto.getLocation());
        assertEquals(5, orderGroupJoinControllerDto.getCount());
        assertEquals(1L, orderGroupJoinControllerDto.getFoodMenuId());
    }

    @Test
    void Post_주문_추가_Dto_Convert() {
        OrderGroupJoinControllerDto orderGroupJoinControllerDto = new OrderGroupJoinControllerDto("testLocation", 5, 1L);

        OrderMenuRegisterCommend orderMenuRegisterCommend = orderGroupJoinControllerDto.convert(2L);

        assertEquals("testLocation", orderMenuRegisterCommend.getLocation());
        assertEquals(5, orderMenuRegisterCommend.getCount());
        assertEquals(1L, orderMenuRegisterCommend.getFoodMenuId());
        assertEquals(2L, orderMenuRegisterCommend.getOrderJoinGroupId());
    }
}