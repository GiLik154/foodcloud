package com.example.foodcloud.controller.core.bank;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.bank.service.account.delete.BankAccountDeleteService;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/bank/delete")
public class BankDeleteController {
    private final BankAccountDeleteService bankAccountDeleteService;
    private final BankAccountRepository bankAccountRepository;

    @GetMapping("")
    public String get(@RequestParam Long bankAccountId, Model model) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(NotFoundBankAccountException::new);

        model.addAttribute("bankAccountInfo", bankAccount);
        return "thymeleaf/bank/delete";
    }

    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId, Long bankAccountId, String password, Model model) {
        model.addAttribute("isDelete", bankAccountDeleteService.delete(userId, bankAccountId, password));

        return "thymeleaf/bank/delete";
    }
}
