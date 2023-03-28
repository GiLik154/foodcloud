package com.example.foodcloud.domain.order.menu.menu.domain;

import com.example.foodcloud.domain.order.menu.item.domain.OrderMenuItems;
import com.example.foodcloud.domain.order.menu.payment.domain.OrderMenuPayment;
import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.BankCode;
import com.example.foodcloud.enums.OrderResult;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Getter
public class OrderMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private String time;
    private OrderResult result;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_menu_id")
    private OrderMain orderMain;

    @OneToOne(cascade = {CascadeType.PERSIST})
    private OrderMenuItems orderMenuItems;

    @OneToOne(cascade = CascadeType.PERSIST)
    private OrderMenuPayment orderMenuPayment;

    public OrderMenu() {
    }

    public OrderMenu(String location, String time, User user, OrderMain orderMain) {
        this.location = location;
        this.time = time;
        this.user = user;
        this.orderMain = orderMain;
        this.result = OrderResult.PAYMENT_WAITING;
    }

    public void updateResult(OrderResult orderResult) {
        this.result = orderResult;
    }
}
