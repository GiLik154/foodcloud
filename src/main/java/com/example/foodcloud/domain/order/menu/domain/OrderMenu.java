package com.example.foodcloud.domain.order.menu.domain;

import com.example.foodcloud.domain.payment.Payment;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.OrderResult;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private FoodMenu foodMenu;
    private int count;
    private int price;
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderMain orderMain;
    private OrderResult result;
    private String time;
    private String location;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;

    public OrderMenu() {
    }

    public OrderMenu(String location, int count, String time, User user, FoodMenu foodMenu, OrderMain orderMain) {
        this.location = location;
        this.count = count;
        this.time = time;
        this.user = user;
        this.foodMenu = foodMenu;
        this.orderMain = orderMain;
        this.price = Math.multiplyExact(foodMenu.getPrice(), count);
        this.result = OrderResult.PAYMENT_WAITING;
    }

    public void updatePayment(Payment payment) {
        this.payment = payment;
        this.result = OrderResult.RECEIVED;
    }

    public void updateResult(OrderResult orderResult) {
        this.result = orderResult;
    }
}
