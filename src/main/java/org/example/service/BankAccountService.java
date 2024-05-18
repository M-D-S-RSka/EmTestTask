package org.example.service;

import org.example.model.BankAccount;
import org.example.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void transferMoney(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        BankAccount fromAccount = bankAccountRepository.findById(fromAccountId).orElseThrow();
        BankAccount toAccount = bankAccountRepository.findById(toAccountId).orElseThrow();

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);
    }

    public BigDecimal getBalance(Long accountId) {
        BankAccount account = bankAccountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }

    public void deposit(Long accountId, BigDecimal amount) {
        BankAccount account = bankAccountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance().add(amount));
        bankAccountRepository.save(account);
    }

    public void withdraw(Long accountId, BigDecimal amount) {
        BankAccount account = bankAccountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        account.setBalance(account.getBalance().subtract(amount));
        bankAccountRepository.save(account);
    }
}