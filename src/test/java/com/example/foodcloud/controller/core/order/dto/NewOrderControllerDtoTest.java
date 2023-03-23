package com.example.foodcloud.controller.core.order.dto;

import com.example.foodcloud.domain.order.main.service.add.dto.NewOrderServiceDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewOrderControllerDtoTest {
    @Test
    void Post_주문_추가_Dto() {
        NewOrderControllerDto newOrderControllerDto = new NewOrderControllerDto("testLocation", 5, 1L, 2L);

        assertEquals("testLocation", newOrderControllerDto.getLocation());
        assertEquals(5, newOrderControllerDto.getCount());
        assertEquals(1L, newOrderControllerDto.getRestaurantId());
        assertEquals(2L, newOrderControllerDto.getFoodMenuId());
    }

    @Test
    void Post_주문_추가_Dto_Convert() {
        NewOrderControllerDto newOrderControllerDto = new NewOrderControllerDto("testLocation", 5, 1L, 2L);

        NewOrderServiceDto newOrderServiceDto = newOrderControllerDto.convert();

        assertEquals("testLocation", newOrderServiceDto.getLocation());
        assertEquals(5, newOrderServiceDto.getCount());
        assertEquals(1L, newOrderServiceDto.getRestaurantId());
        assertEquals(2L, newOrderServiceDto.getFoodMenuId());
    }
}