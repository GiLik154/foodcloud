package com.example.foodcloud.domain.order.menu.domain;

import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.BankCode;
import com.example.foodcloud.enums.OrderResult;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Getter
public class OrderMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    @Min(value = 0)
    private int count;
    private String time;
    private String result;
    @Min(value = 0)
    private int price;

    private String payment;
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
        this.price = Math.multiplyExact(foodMenu.getPrice(), count);
        this.result = OrderResult.PAYMENT_WAITING.getResult();
    }

    public void updatePayment(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        this.payment = bankAccount.getBank();
        this.result = OrderResult.RECEIVED.getResult();
    }

    public void updatePayment(Point point) {
        this.point = point;
        this.payment = BankCode.POINT.getCode();
        this.result = OrderResult.RECEIVED.getResult();
    }

    public void updateResult(String result) {
        this.result = result;
    }
}
