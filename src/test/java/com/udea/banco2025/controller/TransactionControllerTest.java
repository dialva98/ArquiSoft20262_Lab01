package com.udea.banco2025.controller;

import com.udea.banco2025.DTO.TransactionDTO;
import com.udea.banco2025.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;
    @InjectMocks
    private TransactionController transactionController;
    @BeforeEach
    void setUp() {
        transactionController = new TransactionController(transactionService);
    }

    @Test
    void shouldTransferMoney() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setSenderAccountNumber("123456");
        transactionDTO.setReceiverAccountNumber("654321");
        transactionDTO.setAmount(500.0);
        TransactionDTO savedTransaction = new TransactionDTO();
        savedTransaction.setId(1L);
        savedTransaction.setSenderAccountNumber("123456");
        savedTransaction.setReceiverAccountNumber("654321");
        savedTransaction.setAmount(500.0);

        when(transactionService.transferMoney(transactionDTO)).thenReturn(savedTransaction);

        ResponseEntity<?> response = transactionController.transferMoney(transactionDTO);

        assertEquals(200, response.getStatusCode().value());
        assertSame(savedTransaction, response.getBody());
        verify(transactionService).transferMoney(transactionDTO);
    }

    @Test
    void shouldReturnBadRequestWhenTransferFails() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setSenderAccountNumber("123456");
        transactionDTO.setReceiverAccountNumber("654321");
        transactionDTO.setAmount(500.0);

        when(transactionService.transferMoney(transactionDTO)).thenThrow(new IllegalArgumentException("Saldo insuficiente"));

        ResponseEntity<?> response = transactionController.transferMoney(transactionDTO);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Saldo insuficiente", response.getBody());
        verify(transactionService).transferMoney(transactionDTO);
    }

    @Test
    void shouldGetTransactionsByAccount() {
        String accountNumber = "123456";

        TransactionDTO transaction1 = new TransactionDTO();
        transaction1.setId(1L);
        transaction1.setSenderAccountNumber(accountNumber);
        transaction1.setReceiverAccountNumber("654321");
        transaction1.setAmount(500.0);
        TransactionDTO transaction2 = new TransactionDTO();
        transaction2.setId(2L);
        transaction2.setSenderAccountNumber("789012");
        transaction2.setReceiverAccountNumber(accountNumber);
        transaction2.setAmount(250.0);

        when(transactionService.getTransactionsForAccount(accountNumber)).thenReturn(List.of(transaction1, transaction2));

        List<TransactionDTO> result = transactionController.getTransactionsByAccount(accountNumber);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertSame(transaction1, result.get(0));
        assertSame(transaction2, result.get(1));
        verify(transactionService).getTransactionsForAccount(accountNumber);
    }

    @Test
    void shouldReturnEmptyListWhenAccountHasNoTransactions() {
        String accountNumber = "123456";

        when(transactionService.getTransactionsForAccount(accountNumber)).thenReturn(List.of());
        List<TransactionDTO> result = transactionController.getTransactionsByAccount(accountNumber);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(transactionService).getTransactionsForAccount(accountNumber);
    }
}
