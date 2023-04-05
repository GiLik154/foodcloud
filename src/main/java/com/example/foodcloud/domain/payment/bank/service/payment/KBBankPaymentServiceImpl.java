package com.example.foodcloud.domain.payment.bank.service.payment;

import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.order.menu.service.update.payment.OrderMenuPaymentUpdateService;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 결제 서비스 (API 대신 사용)
 */
@Service("004")
@RequiredArgsConstructor
@Transactional
public class KBBankPaymentServiceImpl implements PaymentService {
    private final OrderMenuPaymentUpdateService orderMenuPaymentUpdateService;
    private final BankAccountRepository bankAccountRepository;

    /**
     * userId와 bankAccountId를 통해 검증 후
     * bankAccount를 찾아온다.
     * 메소드를 따로 뺀 이유는 성공과 실패 케이스를 반화해주기 위해서
     * 이후 orderMenuPaymentUpdateService에 orderMenuId와 BankAccount를 반환해준다.
     */
    @Override
    public String pay(Long userId, Long orderMenuId, Long bankAccountId, int price) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, bankAccountId)) {

            orderMenuPaymentUpdateService.update(orderMenuId, getBankAccount(bankAccountId));

            return price + " price KB Bank payment succeed";
        }
        return "KB bank payment fail";
    }

    /**
     * userId와 orderMenu를 받아와서
     * orderMenu에서 Payment의 PK를 찾아와서 검증한다.
     * 이후 성공과 실패 String을 출력한다.
     */
    @Override
    public String refund(Long userId, OrderMenu orderMenu) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, orderMenu.getPayment().getId())) {
            return orderMenu.getPrice() + " price KB Bank refund succeed";
        }
        return "KB bank refund fail";
    }

    private BankAccount getBankAccount(Long bankAccountId) {
        return bankAccountRepository.findById(bankAccountId).orElseThrow(NotFoundBankAccountException::new);
    }
}
