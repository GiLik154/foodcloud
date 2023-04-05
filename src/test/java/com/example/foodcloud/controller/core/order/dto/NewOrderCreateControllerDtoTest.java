package com.example.foodcloud.controller.core.order.dto;

import com.example.foodcloud.domain.order.join.service.add.dto.NewOrderServiceDto;
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

        NewOrderServiceDto newOrderServiceDto = newOrderCreateControllerDto.convert();

        assertEquals("testLocation", newOrderServiceDto.getLocation());
        assertEquals(5, newOrderServiceDto.getCount());
        assertEquals(1L, newOrderServiceDto.getRestaurantId());
        assertEquals(2L, newOrderServiceDto.getFoodMenuId());
    }
}