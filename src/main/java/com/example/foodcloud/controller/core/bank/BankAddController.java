package com.example.foodcloud.controller.core.bank;

import com.example.foodcloud.controller.core.bank.dto.BankAccountAddControllerDto;
import com.example.foodcloud.domain.payment.bank.service.account.add.BankAccountAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/bank")
public class BankAddController {
    private final BankAccountAddService bankAccountAddService;

    @GetMapping("/add")
    public String get() {
        return "thymeleaf/bank/add";
    }

    @PostMapping("/add")
    public String post(@SessionAttribute("userId") Long userId, @Valid BankAccountAddControllerDto bankAccountAddControllerDto) {
         bankAccountAddService.add(userId, bankAccountAddControllerDto.convert());

        return "thymeleaf/bank/add";
    }
}
