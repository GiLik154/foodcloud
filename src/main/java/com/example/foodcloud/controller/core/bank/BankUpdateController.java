package com.example.foodcloud.controller.core.bank;

import com.example.foodcloud.controller.core.bank.dto.BankAccountUpdateControllerDto;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.bank.service.account.update.BankAccountUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * bankAccount를 수정하는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/bank/update")
public class BankUpdateController {
    private final BankAccountUpdateService bankAccountUpdateService;
    private final BankAccountRepository bankAccountRepository;

    /**
     * RequestParam으로 bankAccountId를 받아옴.
     * 이후 수정할 bankAccount를 출력해줌
     */
    @GetMapping("")
    public String get(@RequestParam Long bankAccountId, Model model) {
        bankAccountRepository.findById(bankAccountId).ifPresent(bankAccount ->
                model.addAttribute("bankAccountInfo", bankAccount));


        return "thymeleaf/bank/update";
    }

    /**
     * 세션으로 userId를 받아야와하고
     * RequestParam으로 bankAccountId를 받아옴.
     * Dto를 통해서 값을 받아옴.
     * Dto는 Valid로 검증함
     * 이후 서비스단과 컨트롤단의 분리를 위해
     * Dto.convert 기능을 이요해서 Service Dto로 변환해줌.
     */
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
