package com.example.foodcloud.domain.payment;

import com.example.foodcloud.domain.order.menu.menu.domain.OrderMenu;

public interface Payment {

    void orderMenuUpdate(OrderMenu orderMenu);
}
