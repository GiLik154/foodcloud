package com.example.foodcloud.controller.bank;

import com.example.foodcloud.controller.bank.dto.BankAccountAddControllerDto;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.bank.service.account.add.BankAccountAddService;
import com.example.foodcloud.domain.bank.service.account.delete.BankAccountDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/bank/delete")
public class BankDeleteController {
    private final BankAccountDeleteService bankAccountDeleteService;
    private final BankAccountRepository bankAccountRepository;

    @GetMapping("")
    public String delete(@SessionAttribute("userId") Long userId, Model model) {
        model.addAttribute("bankList", bankAccountRepository.findByUserId(userId));
        return "thymeleaf/bank/delete";
    }

    @PostMapping("")
    public String check(@SessionAttribute("userId") Long userId, Long bankAccountId, String password) {
        bankAccountDeleteService.delete(userId, bankAccountId, password);

        return "thymeleaf/bank/delete";
    }
}
