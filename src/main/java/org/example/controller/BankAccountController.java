package org.example.controller;

import org.example.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping("/transfer")
    public void transferMoney(@RequestParam Long fromAccountId, @RequestParam Long toAccountId,
                              @RequestParam BigDecimal amount) {
        bankAccountService.transferMoney(fromAccountId, toAccountId, amount);
    }

    @GetMapping("/{id}/balance")
    public BigDecimal getBalance(@PathVariable Long id) {
        return bankAccountService.getBalance(id);
    }

    @PostMapping("/{id}/deposit")
    public void deposit(@PathVariable Long id, @RequestParam BigDecimal amount) {
        bankAccountService.deposit(id, amount);
    }

    @PostMapping("/{id}/withdraw")
    public void withdraw(@PathVariable Long id, @RequestParam BigDecimal amount) {
        bankAccountService.withdraw(id, amount);
    }
}