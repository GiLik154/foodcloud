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

/**
 * 계좌를 추가하는 Controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/bank")
public class BankAddController {
    private final BankAccountAddService bankAccountAddService;

    @GetMapping("/add")
    public String get() {
        return "thymeleaf/bank/add";
    }

    /**
     * 세션으로 userId를 받아야와하고
     * Dto를 통해서 값을 받아옴.
     * Dto는 Valid로 검증함
     * 이후 서비스단과 컨트롤단의 분리를 위해
     * Dto.convert 기능을 이요해서 Service Dto로 변환해줌.
     */
    @PostMapping("/add")
    public String post(@SessionAttribute("userId") Long userId,
                       @Valid BankAccountAddControllerDto bankAccountAddControllerDto) {
        bankAccountAddService.add(userId, bankAccountAddControllerDto.convert());

        return "thymeleaf/bank/add";
    }
}
