package com.example.foodcloud.controller.core.bank;

import com.example.foodcloud.controller.core.bank.dto.BankAccountUpdateControllerDto;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.bank.service.account.update.BankAccountUpdateService;
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
        bankAccountRepository.findById(bankAccountId).ifPresent(bankAccount ->
                model.addAttribute("bankAccountInfo", bankAccount));


        return "thymeleaf/bank/update";
    }

    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId,
                       @RequestParam Long bankAccountId,
                       @Valid BankAccountUpdateControllerDto bankAccountUpdateControllerDto,
                       Model model) {

        model.addAttribute("isUpdate", bankAccountUpdateService.update(userId,
                bankAccountId,
                bankAccountUpdateControllerDto.convert()));

        return "redirect:/bank/list";
    }
}
