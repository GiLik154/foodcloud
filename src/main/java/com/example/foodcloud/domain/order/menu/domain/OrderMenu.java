package com.example.foodcloud.domain.order.menu.domain;

import com.example.foodcloud.domain.payment.domain.Payment;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroup;
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
    /**
     * Order에 담겨있는 FoodMenu
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private FoodMenu foodMenu;
    /**
     * 음식 주문 갯수
     */
    private int count;
    /**
     * 총 가격 (음식가격 x 갯수)
     */
    private int price;
    /**
     * OrderMenu를 공구하는 메인 주문 리스트
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderJoinGroup orderJoinGroup;
    /**
     * OrderMenu의 결과
     */
    private OrderResult result;
    /**
     * 주문 시간
     */
    private String time;
    /**
     * 주문 장소
     */
    private String location;
    /**
     * 주문한 유저
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    /**
     * 주문한 결제 수단
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;

    protected OrderMenu() {
    }

    /**
     * OrderMenu 기본 생성자
     */
    public OrderMenu(String location, int count, String time, User user, FoodMenu foodMenu, OrderJoinGroup orderJoinGroup) {
        this.location = location;
        this.count = count;
        this.time = time;
        this.user = user;
        this.foodMenu = foodMenu;
        this.orderJoinGroup = orderJoinGroup;
        this.price = Math.multiplyExact(foodMenu.getPrice(), count);
        this.result = OrderResult.PAYMENT_WAITING;
    }

    /**
     * 결제를 처리하고, 주문 상태를 "RECEIVED"로 변경
     *
     * @param payment 결제 정보
     */
    public void completeOrderWithPayment(Payment payment) {
        this.payment = payment;
        this.result = OrderResult.RECEIVED;
    }

    /**
     * 결과를 변경함
     */
    public void updateResult(OrderResult orderResult) {
        this.result = orderResult;
    }
}
