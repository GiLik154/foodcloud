package com.example.foodcloud.service.bank_account;

import com.example.foodcloud.entity.BankAccount;
import com.example.foodcloud.entity.BankAccountRepository;
import com.example.foodcloud.entity.User;
import com.example.foodcloud.entity.UserRepository;
import com.example.foodcloud.service.bank_account.dto.BankAccountDto;
import com.example.foodcloud.service.user.UserJoinService;
import com.example.foodcloud.service.user.dto.JoinServiceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BankAccountAddServiceImplTest {
    private final BankAccountAddService bankAccountAddService;
    private final UserJoinService userJoinService;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    @Autowired
    BankAccountAddServiceImplTest(BankAccountAddService bankAccountAddService, UserJoinService userJoinService, BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountAddService = bankAccountAddService;
        this.userJoinService = userJoinService;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 계좌_추가_정상작동() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.join(joinServiceDto);

        User user = userRepository.findUser("test");
        Long id = user.getId();

        BankAccountDto bankAccountDto = new BankAccountDto("test", "test", 01, id);
        bankAccountAddService.add(bankAccountDto);

        BankAccount bankAccount = bankAccountRepository.findBankAccountByUserId(id).get(0);

        assertThat(bankAccount.getUser()).isEqualTo(user);
        assertThat(bankAccount.getName()).isEqualTo("test");
        assertThat(bankAccount.getAccountNumber()).isEqualTo("test");
        assertThat(bankAccount.getBank()).isEqualTo(1);
    }
}