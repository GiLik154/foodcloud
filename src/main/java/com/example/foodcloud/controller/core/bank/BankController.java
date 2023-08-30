package com.example.foodcloud.controller.core.bank;

import com.example.foodcloud.controller.core.bank.req.BankAccountRegisterReq;
import com.example.foodcloud.controller.core.bank.req.BankAccountUpdateReq;
import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.service.bank.BankAccountDeleter;
import com.example.foodcloud.domain.payment.service.bank.BankAccountRegister;
import com.example.foodcloud.domain.payment.service.bank.BankAccountUpdater;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import com.example.foodcloud.security.login.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/bank")
public class BankController {
    private final BankAccountRepository bankAccountRepository;

    private final BankAccountRegister bankAccountRegister;
    private final BankAccountUpdater bankAccountUpdater;
    private final BankAccountDeleter bankAccountDeleter;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "thymeleaf/bank/register";
    }


    @PostMapping("/register")
    public String register(@Valid BankAccountRegisterReq req) {
        Long userId = getCurrentUserId();

        bankAccountRegister.register(userId, req.convert());

        return "thymeleaf/bank/register";
    }

    @GetMapping("/list")
    public String showListList(Model model) {
        Long userId = getCurrentUserId();

        model.addAttribute("bankAccountList", bankAccountRepository.findByUserId(userId));

        return "thymeleaf/bank/list";
    }

    @GetMapping("/update/{bankAccountId}")
    public String showUpdatePage(@PathVariable Long bankAccountId, Model model) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId).orElseThrow(() ->
                new NotFoundBankAccountException("Not found BankAccount : UpdatePage"));

        model.addAttribute("bankAccountInfo", bankAccount);

        return "thymeleaf/bank/update";
    }

    @PutMapping("/{bankAccountId}")
    public String update(@PathVariable Long bankAccountId,
                         @Valid BankAccountUpdateReq req) {
        Long userId = getCurrentUserId();

        bankAccountUpdater.update(userId, bankAccountId, req.convert());

        return "redirect:/bank/list";
    }

    @GetMapping("/delete/{bankAccountId}")
    public String showDeletePage(@PathVariable Long bankAccountId, Model model) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId).orElseThrow(() ->
                new NotFoundBankAccountException("Not found BankAccount : UpdatePage"));

        model.addAttribute("bankAccountInfo", bankAccount);


        return "thymeleaf/bank/delete";
    }

    @DeleteMapping("/{bankAccountId}")
    public String delete(@PathVariable Long bankAccountId, String password) {
        Long userId = getCurrentUserId();

        bankAccountDeleter.delete(userId, bankAccountId, password);

        return "redirect:/bank/list";
    }

    private Long getCurrentUserId() {
        UserDetail userDetail = getUserDetail();

        return userDetail.getUserId();
    }

    private UserDetail getUserDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (UserDetail) authentication.getPrincipal();
    }
}
