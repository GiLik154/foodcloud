package com.example.foodcloud.controller.bank;

import com.example.foodcloud.controller.bank.dto.BankAccountUpdateControllerDto;
import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.bank.service.account.update.BankAccountUpdateService;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/bank/update")
public class BankUpdateController {
    private final BankAccountUpdateService bankAccountUpdateService;
    private final BankAccountRepository bankAccountRepository;

    @GetMapping("")
    public String get(@RequestParam Long bankAccountId, Model model) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(NotFoundBankAccountException::new);

        model.addAttribute("bankAccountInfo", bankAccount);
        return "thymeleaf/bank/update";
    }

    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId,
                        Long bankAccountId,
                        @Valid BankAccountUpdateControllerDto bankAccountUpdateControllerDto,
                        Model model) {

        model.addAttribute("isUpdate", bankAccountUpdateService.update(userId,
                bankAccountId,
                bankAccountUpdateControllerDto.convert()));

        return "thymeleaf/bank/update";
    }
}
