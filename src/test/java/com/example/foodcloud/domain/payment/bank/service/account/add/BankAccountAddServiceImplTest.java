package com.example.foodcloud.domain.payment.bank.service.account.add;

import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.payment.bank.service.account.add.dto.BankAccountAddServiceDto;
import com.example.foodcloud.enums.PaymentCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BankAccountAddServiceImplTest {
    private final BankAccountAddService bankAccountAddService;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    @Autowired
    BankAccountAddServiceImplTest(BankAccountAddService bankAccountAddService, BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountAddService = bankAccountAddService;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 계좌_추가_정상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccountAddServiceDto bankAccountAddServiceDto = new BankAccountAddServiceDto("testName", "testNumber", PaymentCode.KB);
        bankAccountAddService.add(userId, bankAccountAddServiceDto);

        BankAccount bankAccount = bankAccountRepository.findByUserId(userId).get(0);


        assertNotNull(bankAccount.getId());
        assertEquals(user, bankAccount.getUser());
        assertEquals("testName", bankAccount.getName());
        assertEquals("testNumber", bankAccount.getAccountNumber());
        assertEquals(PaymentCode.KB, bankAccount.getPaymentCode());
    }

    @Test
    void 계좌_추가_유저_없음() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccountAddServiceDto bankAccountAddServiceDto = new BankAccountAddServiceDto("testName", "testNumber", PaymentCode.KB);

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                bankAccountAddService.add(userId + 1L, bankAccountAddServiceDto)
        );
        assertEquals("Invalid user", e.getMessage());
    }
}