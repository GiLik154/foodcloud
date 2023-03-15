package com.example.foodcloud.domain.order.menu.service.update.payment;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("PaymentUpdateForBank")
@Transactional
@RequiredArgsConstructor
public class OrderMenuPaymentBankUpdateService implements OrderMenuPaymentUpdateService {
    private final OrderMenuRepository orderMenuRepository;

    public boolean isUpdate(Long orderMenuId, Object payment) {
        Optional<OrderMenu> orderMenuOptional = orderMenuRepository.findById(orderMenuId);
        if (orderMenuOptional.isPresent()) {
            OrderMenu orderMenu = orderMenuOptional.get();

            orderMenu.updatePaymentForBank(payment);

            return true;

        }
        return false;
    }
}
