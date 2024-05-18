package org.example.scheduler;

import org.example.model.BankAccount;
import org.example.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class InterestScheduler {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Scheduled(fixedRate = 60000)
    public void applyInterest() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        for (BankAccount account : accounts) {
            BigDecimal newBalance = account.getBalance().multiply(BigDecimal.valueOf(1.05));
            BigDecimal maxBalance = account.getInitialDeposit().multiply(BigDecimal.valueOf(2.07));
            if (newBalance.compareTo(maxBalance) > 0) {
                newBalance = maxBalance;
            }
            account.setBalance(newBalance);
            bankAccountRepository.save(account);
        }
    }
}