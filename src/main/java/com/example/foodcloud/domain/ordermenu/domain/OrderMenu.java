package com.example.foodcloud.domain.ordermenu.domain;

import com.example.foodcloud.domain.payment.domain.Payment;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.OrderResult;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class OrderMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 음식 주문 갯수 */
    @Column(nullable = false)
    private int count;

    /** 총 가격 (음식가격 x 갯수) */
    @Column(nullable = false)
    private int price;

    /** 주문 시간 */
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime time;

    /** 주문 장소 */
    @Column(nullable = false)
    private String location;

    /** 주문한 유저 */
    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /** OrderMenu를 공구하는 메인 주문 리스트 */
    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private GroupBuyList groupBuyList;

    /** 주문한 결제 수단 */
    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;

    /** Order에 담겨있는 FoodMenu */
    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private FoodMenu foodMenu;

    /** OrderMenu의 결과 */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderResult result;

    protected OrderMenu() {}

    /** OrderMenu 기본 생성자 */
    public OrderMenu(int count, String location,  User user, GroupBuyList groupBuyList, FoodMenu foodMenu) {
        this.count = count;
        this.price = Math.multiplyExact(foodMenu.getPrice(), count);
        this.location = location;
        this.user = user;
        this.groupBuyList = groupBuyList;
        this.foodMenu = foodMenu;
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
