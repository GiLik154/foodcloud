package com.example.foodcloud.controller.bank;

import com.example.foodcloud.controller.bank.dto.BankAccountAddControllerDto;
import com.example.foodcloud.domain.bank.service.account.add.BankAccountAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/bank/update")
public class BankUpdateController {
    private final BankAccountAddService bankAccountAddService;

    @GetMapping("")
    public String add() {
        return "add";
    }

    @PostMapping("")
    public String check(@SessionAttribute("userId") Long userId, @Valid BankAccountAddControllerDto bankAccountAddControllerDto) {

        bankAccountAddService.add(userId, bankAccountAddControllerDto.convert());

        return "add";
    }
}
