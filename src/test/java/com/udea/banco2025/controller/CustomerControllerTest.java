package com.udea.banco2025.controller;

import com.udea.banco2025.DTO.CustomerDTO;
import com.udea.banco2025.service.CustomerService;
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
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;
    @InjectMocks
    private CustomerController customerController;
    @BeforeEach
    void setUp() {customerController = new CustomerController(customerService);}

    @Test
    void shouldGetAllCustomers() {
        CustomerDTO customer1 = new CustomerDTO();
        CustomerDTO customer2 = new CustomerDTO();
        when(customerService.getAllCustomers()).thenReturn(List.of(customer1, customer2));

        ResponseEntity<List<CustomerDTO>> response = customerController.getAllCustomers();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(customerService).getAllCustomers();
    }

    @Test
    void shouldGetCustomerById() {
        Long id = 1L;
        CustomerDTO customerDTO = new CustomerDTO();

        when(customerService.getCustomerById(id)).thenReturn(customerDTO);
        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(id);

        assertEquals(200, response.getStatusCode().value());
        assertSame(customerDTO, response.getBody());
        verify(customerService).getCustomerById(id);
    }

    @Test
    void shouldCreateCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setBalance(1000.0);
        CustomerDTO savedCustomer = new CustomerDTO();
        savedCustomer.setBalance(1000.0);

        when(customerService.createCustomer(customerDTO)).thenReturn(savedCustomer);

        ResponseEntity<CustomerDTO> response = customerController.createCustomer(customerDTO);

        assertEquals(200, response.getStatusCode().value());
        assertSame(savedCustomer, response.getBody());
        verify(customerService).createCustomer(customerDTO);
    }

    @Test
    void shouldRejectCustomerWithNullBalance() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setBalance(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerController.createCustomer(customerDTO)
        );

        assertEquals("Saldo no puede ser nulo", exception.getMessage());
        verify(customerService, never()).createCustomer(any());
    }
}
