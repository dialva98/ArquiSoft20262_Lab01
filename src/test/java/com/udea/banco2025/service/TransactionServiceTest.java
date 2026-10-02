package com.udea.banco2025.service;

import com.udea.banco2025.DTO.TransactionDTO;
import com.udea.banco2025.entity.Customer;
import com.udea.banco2025.entity.Transaction;
import com.udea.banco2025.repository.CustomerRepository;
import com.udea.banco2025.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private TransactionService transactionService;

    private Customer sender;
    private Customer receiver;
    private TransactionDTO transactionDTO;

    @BeforeEach
    void setUp() {
        sender = new Customer();
        sender.setAccountNumber("1001");
        sender.setBalance(1000.0);
        receiver = new Customer();
        receiver.setAccountNumber("2002");
        receiver.setBalance(500.0);
        transactionDTO = new TransactionDTO();
        transactionDTO.setSenderAccountNumber("1001");
        transactionDTO.setReceiverAccountNumber("2002");
        transactionDTO.setAmount(300.0);
    }

    @Test
    void transferMoneyShouldRejectNullAccounts() {
        transactionDTO.setSenderAccountNumber(null);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.transferMoney(transactionDTO)
        );
        assertEquals(
                "Las cuentas remitente y destino no pueden ser nulas",
                exception.getMessage()
        );
        verifyNoInteractions(customerRepository, transactionRepository);
    }

    @Test
    void transferMoneyShouldRejectUnknownSenderAccount() {
        when(customerRepository.findByAccountNumber("1001"))
                .thenReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.transferMoney(transactionDTO)
        );
        assertEquals(
                "Cuenta remitente no fue encontrada",
                exception.getMessage()
        );
        verify(customerRepository).findByAccountNumber("1001");
        verify(customerRepository, never()).save(any(Customer.class));
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void transferMoneyShouldRejectUnknownReceiverAccount() {
        when(customerRepository.findByAccountNumber("1001")).thenReturn(Optional.of(sender));
        when(customerRepository.findByAccountNumber("2002")).thenReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.transferMoney(transactionDTO)
        );
        assertEquals(
                "Cuenta destino no fue encontrada",
                exception.getMessage()
        );
        verify(customerRepository).findByAccountNumber("1001");
        verify(customerRepository).findByAccountNumber("2002");
        verify(customerRepository, never()).save(any(Customer.class));
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void transferMoneyShouldRejectInsufficientBalance() {
        transactionDTO.setAmount(1500.0);
        when(customerRepository.findByAccountNumber("1001")).thenReturn(Optional.of(sender));
        when(customerRepository.findByAccountNumber("2002")).thenReturn(Optional.of(receiver));
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.transferMoney(transactionDTO)
        );
        assertEquals(
                "Saldo insuficiente en la cuente remitente",
                exception.getMessage()
        );

        assertEquals(1000.0, sender.getBalance());
        assertEquals(500.0, receiver.getBalance());
        verify(customerRepository, never()).save(any(Customer.class));
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void transferMoneyShouldTransferMoneySuccessfully() {
        Transaction savedTransaction = new Transaction();
        savedTransaction.setId(1L);
        savedTransaction.setSenderAccountNumber("1001");
        savedTransaction.setReceiverAccountNumber("2002");
        savedTransaction.setAmount(300.0);

        when(customerRepository.findByAccountNumber("1001")).thenReturn(Optional.of(sender));
        when(customerRepository.findByAccountNumber("2002")).thenReturn(Optional.of(receiver));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);
        TransactionDTO result = transactionService.transferMoney(transactionDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("1001", result.getSenderAccountNumber());
        assertEquals("2002", result.getReceiverAccountNumber());
        assertEquals(300.0, result.getAmount());
        assertEquals(700.0, sender.getBalance());
        assertEquals(800.0, receiver.getBalance());
        verify(customerRepository).findByAccountNumber("1001");
        verify(customerRepository).findByAccountNumber("2002");
        verify(customerRepository).save(sender);
        verify(customerRepository).save(receiver);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void getTransactionsForAccountShouldReturnTransactionsAsDTOs() {
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setSenderAccountNumber("1001");
        transaction1.setReceiverAccountNumber("2002");
        transaction1.setAmount(300.0);
        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setSenderAccountNumber("3003");
        transaction2.setReceiverAccountNumber("1001");
        transaction2.setAmount(150.0);
        when(transactionRepository
                .findBySenderAccountNumberOrReceiverAccountNumber("1001", "1001"))
                .thenReturn(List.of(transaction1, transaction2));
        List<TransactionDTO> result =
                transactionService.getTransactionsForAccount("1001");
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("1001", result.get(0).getSenderAccountNumber());
        assertEquals("2002", result.get(0).getReceiverAccountNumber());
        assertEquals(300.0, result.get(0).getAmount());
        assertEquals(2L, result.get(1).getId());
        assertEquals("3003", result.get(1).getSenderAccountNumber());
        assertEquals("1001", result.get(1).getReceiverAccountNumber());
        assertEquals(150.0, result.get(1).getAmount());
        verify(transactionRepository)
                .findBySenderAccountNumberOrReceiverAccountNumber("1001", "1001");
    }
}
