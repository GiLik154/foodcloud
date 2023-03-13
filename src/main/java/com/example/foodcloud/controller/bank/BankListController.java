package com.example.foodcloud.controller.bank;

import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.enums.BankCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/bank/list")
public class BankListController {
    private final BankAccountRepository bankAccountRepository;

    @GetMapping("")
    public String get(@SessionAttribute("userId") Long userId, Model model) {
        model.addAttribute("bankAccountList", bankAccountRepository.findByUserId(userId));

        return "thymeleaf/bank/list";
    }
}
