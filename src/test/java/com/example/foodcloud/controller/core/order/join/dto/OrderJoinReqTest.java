package com.example.foodcloud.controller.core.order.join.dto;

import com.example.foodcloud.controller.core.order.req.OrderJoinReq;
import com.example.foodcloud.domain.ordermenu.service.commend.OrderMenuCreatorCommend;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderJoinReqTest {

    @Test
    void Post_주문_추가_Dto() {
        OrderJoinReq orderJoinReq = new OrderJoinReq("testLocation", 5, 1L);

        assertEquals("testLocation", orderJoinReq.getLocation());
        assertEquals(5, orderJoinReq.getCount());
        assertEquals(1L, orderJoinReq.getFoodMenuId());
    }

    @Test
    void Post_주문_추가_Dto_Convert() {
        OrderJoinReq orderJoinReq = new OrderJoinReq("testLocation", 5, 1L);

        OrderMenuCreatorCommend orderMenuCreatorCommend = orderJoinReq.convert(2L);

        assertEquals("testLocation", orderMenuCreatorCommend.getLocation());
        assertEquals(5, orderMenuCreatorCommend.getCount());
        assertEquals(1L, orderMenuCreatorCommend.getFoodMenuId());
        assertEquals(2L, orderMenuCreatorCommend.getOrderJoinGroupId());
    }
}