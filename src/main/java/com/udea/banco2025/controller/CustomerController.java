/*
 * Copyright (c) 2026 Diego. All rights reserved.
 */

package com.udea.banco2025.controller;
import com.udea.banco2025.DTO.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.udea.banco2025.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    //obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    //obtener un cliente por un ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    //Crear un cliente
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        if(customerDTO.getBalance() == null){
            throw new IllegalArgumentException("Saldo no puede ser nulo");
        }
        return ResponseEntity.ok(customerService.createCustomer(customerDTO));

    }


}