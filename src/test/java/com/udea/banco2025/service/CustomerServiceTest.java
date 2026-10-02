package com.udea.banco2025.service;

import com.udea.banco2025.DTO.CustomerDTO;
import com.udea.banco2025.entity.Customer;
import com.udea.banco2025.mapper.CustomerMapper;
import com.udea.banco2025.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;
    @InjectMocks
    private CustomerService customerService;

    @Test
    void getAllCustomers_shouldReturnAllCustomers() {
        Customer customer1 = new Customer(1L, "1001", "Juan", "Perez", 1000.0);
        Customer customer2 = new Customer(2L, "1002", "Maria", "Gomez", 2000.0);
        CustomerDTO dto1 = new CustomerDTO(1L, "Juan", "Perez", "1001", 1000.0);
        CustomerDTO dto2 = new CustomerDTO(2L, "Maria", "Gomez", "1002", 2000.0);

        when(customerRepository.findAll()).thenReturn(List.of(customer1, customer2));
        when(customerMapper.toDTO(customer1)).thenReturn(dto1);
        when(customerMapper.toDTO(customer2)).thenReturn(dto2);

        List<CustomerDTO> result = customerService.getAllCustomers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Juan", result.get(0).getFirstName());
        assertEquals("Maria", result.get(1).getFirstName());
        verify(customerRepository).findAll();
        verify(customerMapper).toDTO(customer1);
        verify(customerMapper).toDTO(customer2);
    }


    @Test
    void getCustomerById_shouldReturnCustomer_whenCustomerExists() {
        Long customerId = 1L;
        Customer customer = new Customer(customerId, "1001", "Juan", "Perez", 1000.0);
        CustomerDTO customerDTO = new CustomerDTO(customerId, "Juan", "Perez", "1001", 1000.0);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.getCustomerById(customerId);

        assertNotNull(result);
        assertEquals(customerId, result.getId());
        assertEquals("Juan", result.getFirstName());
        assertEquals("Perez", result.getLastName());
        assertEquals("1001", result.getAccountNumber());
        assertEquals(1000.0, result.getBalance());
        verify(customerRepository).findById(customerId);
        verify(customerMapper).toDTO(customer);
    }


    @Test
    void getCustomerById_shouldThrowException_whenCustomerDoesNotExist() {
        Long customerId = 999L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> customerService.getCustomerById(customerId)
        );
        assertEquals("Cliente no encontrado", exception.getMessage());
        verify(customerRepository).findById(customerId);
        verify(customerMapper, never()).toDTO(any(Customer.class));
    }

    @Test
    void createCustomer_shouldSaveAndReturnCustomer() {

        CustomerDTO customerDTO = new CustomerDTO(null, "Carlos", "Rodriguez", "1003", 1500.0);
        Customer customer = new Customer(null, "1003", "Carlos", "Rodriguez", 1500.0);
        Customer savedCustomer = new Customer(3L, "1003", "Carlos", "Rodriguez", 1500.0);
        CustomerDTO savedCustomerDTO = new CustomerDTO(3L, "Carlos", "Rodriguez", "1003", 1500.0);

        when(customerMapper.toEntity(customerDTO)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(savedCustomer);
        when(customerMapper.toDTO(savedCustomer)).thenReturn(savedCustomerDTO);

        CustomerDTO result = customerService.createCustomer(customerDTO);

        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("Carlos", result.getFirstName());
        assertEquals("Rodriguez", result.getLastName());
        assertEquals("1003", result.getAccountNumber());
        assertEquals(1500.0, result.getBalance());
        verify(customerMapper).toEntity(customerDTO);
        verify(customerRepository).save(customer);
        verify(customerMapper).toDTO(savedCustomer);
    }
}
