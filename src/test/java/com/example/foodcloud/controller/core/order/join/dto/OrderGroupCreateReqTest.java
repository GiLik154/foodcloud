package com.example.foodcloud.controller.core.order.join.dto;

import com.example.foodcloud.controller.core.groupbylist.req.OrderGroupCreateReq;
import com.example.foodcloud.domain.ordermenu.service.commend.OrderMenuCreatorCommend;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderGroupCreateReqTest {

    @Test
    void Post_주문_추가_Dto() {
        OrderGroupCreateReq orderGroupCreateReq = new OrderGroupCreateReq("testLocation", 5, 1L);

        assertEquals("testLocation", orderGroupCreateReq.getLocation());
        assertEquals(5, orderGroupCreateReq.getCount());
        assertEquals(1L, orderGroupCreateReq.getFoodMenuId());
    }

    @Test
    void Post_주문_추가_Dto_Convert() {
        OrderGroupCreateReq orderGroupCreateReq = new OrderGroupCreateReq("testLocation", 5, 1L);

        OrderMenuCreatorCommend orderMenuCreatorCommend = orderGroupCreateReq.convert(2L);

        assertEquals("testLocation", orderMenuCreatorCommend.getLocation());
        assertEquals(5, orderMenuCreatorCommend.getCount());
        assertEquals(1L, orderMenuCreatorCommend.getFoodMenuId());
        assertEquals(2L, orderMenuCreatorCommend.getOrderJoinGroupId());
    }
}