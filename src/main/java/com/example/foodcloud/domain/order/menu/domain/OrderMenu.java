package com.example.foodcloud.domain.order.menu.domain;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.point.domain.Point;
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
    private String location;
    private int count;
    private String time;
    private String result;
    private int price;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne()
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;
    @ManyToOne()
    @JoinColumn(name = "point_id")
    private Point point;
    @ManyToOne()
    @JoinColumn(name = "food_menu_id")
    private FoodMenu foodMenu;
    @ManyToOne()
    @JoinColumn(name = "order_menu_id")
    private OrderMain orderMain;

    public OrderMenu() {

    }

    public OrderMenu(String location, int count, String time, User user, FoodMenu foodMenu, OrderMain orderMain) {
        this.location = location;
        this.count = count;
        this.time = time;
        this.user = user;
        this.foodMenu = foodMenu;
        this.orderMain = orderMain;
        this.price = foodMenu.getPrice() * count;
        this.result = OrderResult.PAYMENT_WAITING.getResult();
    }

    public void updatePaymentForBank(Object bankAccount) {
        this.bankAccount = bankAccount;
        this.result = OrderResult.RECEIVED.getResult();
    }

    public void updatePaymentForPoint(Object point) {
        this.point = point;
        this.result = OrderResult.RECEIVED.getResult();
    }

    public void updateResult(String result) {
        this.result = result;
    }
}
