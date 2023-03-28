package com.example.foodcloud.domain.order.menu.payment.domain;

import com.example.foodcloud.exception.NotFoundFoodMenuException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuPaymentRepository extends JpaRepository<OrderMenuPayment, Long> {

    default OrderMenuPayment validate(Long oderMenuId){
        return findById(oderMenuId).orElseThrow(NotFoundFoodMenuException::new);
    }
}
