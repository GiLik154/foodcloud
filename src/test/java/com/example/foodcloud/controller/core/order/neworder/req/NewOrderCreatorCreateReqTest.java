package com.example.foodcloud.controller.core.order.neworder.req;

import com.example.foodcloud.controller.core.order.req.NewOrderCreateReq;
import com.example.foodcloud.application.order.commend.NewOrderServiceCommend;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewOrderCreatorCreateReqTest {
    @Test
    void Post_주문_추가_Dto() {
        NewOrderCreateReq newOrderCreateReq = new NewOrderCreateReq("testLocation", 5, 1L, 2L);

        assertEquals("testLocation", newOrderCreateReq.getLocation());
        assertEquals(5, newOrderCreateReq.getCount());
        assertEquals(1L, newOrderCreateReq.getRestaurantId());
        assertEquals(2L, newOrderCreateReq.getFoodMenuId());
    }

    @Test
    void Post_주문_추가_Dto_Convert() {
        NewOrderCreateReq newOrderCreateReq = new NewOrderCreateReq("testLocation", 5, 1L, 2L);

        NewOrderServiceCommend newOrderServiceCommend = newOrderCreateReq.convert();

        assertEquals("testLocation", newOrderServiceCommend.getLocation());
        assertEquals(5, newOrderServiceCommend.getCount());
        assertEquals(1L, newOrderServiceCommend.getRestaurantId());
        assertEquals(2L, newOrderServiceCommend.getFoodMenuId());
    }
}