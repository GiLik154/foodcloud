package com.example.foodcloud.controller.core.order.neworder.dto;

import com.example.foodcloud.controller.core.order.dto.NewOrderCreateControllerDto;
import com.example.foodcloud.application.order.commend.NewOrderServiceCommend;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewOrderCreateControllerDtoTest {
    @Test
    void Post_주문_추가_Dto() {
        NewOrderCreateControllerDto newOrderCreateControllerDto = new NewOrderCreateControllerDto("testLocation", 5, 1L, 2L);

        assertEquals("testLocation", newOrderCreateControllerDto.getLocation());
        assertEquals(5, newOrderCreateControllerDto.getCount());
        assertEquals(1L, newOrderCreateControllerDto.getRestaurantId());
        assertEquals(2L, newOrderCreateControllerDto.getFoodMenuId());
    }

    @Test
    void Post_주문_추가_Dto_Convert() {
        NewOrderCreateControllerDto newOrderCreateControllerDto = new NewOrderCreateControllerDto("testLocation", 5, 1L, 2L);

        NewOrderServiceCommend newOrderServiceCommend = newOrderCreateControllerDto.convert();

        assertEquals("testLocation", newOrderServiceCommend.getLocation());
        assertEquals(5, newOrderServiceCommend.getCount());
        assertEquals(1L, newOrderServiceCommend.getRestaurantId());
        assertEquals(2L, newOrderServiceCommend.getFoodMenuId());
    }
}