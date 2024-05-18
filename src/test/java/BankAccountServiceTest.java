
import org.example.model.BankAccount;
import org.example.repository.BankAccountRepository;
import org.example.service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountService bankAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBalance() {
        BankAccount account = new BankAccount();
        account.setId(1L);
        account.setBalance(new BigDecimal("100.00"));

        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(account));

        BigDecimal balance = bankAccountService.getBalance(1L);
        assertEquals(new BigDecimal("100.00"), balance);
    }

    @Test
    void testDeposit() {
        BankAccount account = new BankAccount();
        account.setId(1L);
        account.setBalance(new BigDecimal("100.00"));

        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(account));

        bankAccountService.deposit(1L, new BigDecimal("50.00"));
        assertEquals(new BigDecimal("150.00"), account.getBalance());
        verify(bankAccountRepository, times(1)).save(account);
    }

    @Test
    void testWithdraw() {
        BankAccount account = new BankAccount();
        account.setId(1L);
        account.setBalance(new BigDecimal("100.00"));

        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(account));

        bankAccountService.withdraw(1L, new BigDecimal("50.00"));
        assertEquals(new BigDecimal("50.00"), account.getBalance());
        verify(bankAccountRepository, times(1)).save(account);
    }

    @Test
    void testWithdrawInsufficientFunds() {
        BankAccount account = new BankAccount();
        account.setId(1L);
        account.setBalance(new BigDecimal("100.00"));

        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(IllegalArgumentException.class, () -> bankAccountService.withdraw(1L, new BigDecimal("150.00")));
    }
}