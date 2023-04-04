package com.example.foodcloud.controller.core.bank;

import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * 계좌 리스트를 보여주는 컨트롤
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/bank/list")
public class BankListController {
    private final BankAccountRepository bankAccountRepository;

    /**
     * 세션을 통해 userId를 받아옴.
     * userId로 모든 bankAccount를 출력해줌
     */
    @GetMapping("")
    public String get(@SessionAttribute("userId") Long userId, Model model) {
        model.addAttribute("bankAccountList", bankAccountRepository.findByUserId(userId));

        return "thymeleaf/bank/list";
    }
}
