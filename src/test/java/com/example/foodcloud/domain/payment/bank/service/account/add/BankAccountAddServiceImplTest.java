package com.example.foodcloud.domain.payment.bank.service.account.add;

import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.payment.bank.service.account.add.dto.BankAccountAddServiceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        BankAccountAddServiceDto bankAccountAddServiceDto = new BankAccountAddServiceDto("test", "test", "001");
        bankAccountAddService.add(userId, bankAccountAddServiceDto);

        BankAccount bankAccount = bankAccountRepository.findByUserId(userId).get(0);

        assertThat(bankAccount.getUser()).isEqualTo(user);
        assertThat(bankAccount.getName()).isEqualTo("test");
        assertThat(bankAccount.getAccountNumber()).isEqualTo("test");
        assertThat(bankAccount.getBank()).isEqualTo("001");
    }

    @Test
    void 계좌_추가_유저_없음() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccountAddServiceDto bankAccountAddServiceDto = new BankAccountAddServiceDto("test", "test", "001");

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                bankAccountAddService.add(userId +1L, bankAccountAddServiceDto)
        );
        assertEquals("Invalid user", e.getMessage());
    }
}