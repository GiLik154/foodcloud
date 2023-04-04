package com.example.foodcloud.controller.core.bank;

import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.bank.service.account.delete.BankAccountDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 계좌를 삭제하는 컨트롤단
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/bank/delete")
public class BankDeleteController {
    private final BankAccountDeleteService bankAccountDeleteService;
    private final BankAccountRepository bankAccountRepository;

    /**
     * RequestParm을 통해 bankAccountId를 받아옴.
     * 이후 삭제하려는 계좌를 출력해줌
     */
    @GetMapping("")
    public String get(@RequestParam Long bankAccountId, Model model) {
        bankAccountRepository.findById(bankAccountId).ifPresent(bankAccount ->
                model.addAttribute("bankAccountInfo", bankAccount));


        return "thymeleaf/bank/delete";
    }

    /**
     * RequestParm을 통해 bankAccountId를 받아옴.
     * 세션을 통해 userId를 받아옴.
     * String으로 password를 받아와서 처리함
     */
    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId, @RequestParam Long bankAccountId, String password, Model model) {
        model.addAttribute("isDelete", bankAccountDeleteService.delete(userId, bankAccountId, password));

        return "redirect:/bank/list";
    }
}
