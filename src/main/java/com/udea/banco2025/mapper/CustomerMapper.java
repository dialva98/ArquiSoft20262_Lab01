/*
 * Copyright (c) 2026 Diego. All rights reserved.
 */

package com.udea.banco2025.mapper;
import com.udea.banco2025.DTO.CustomerDTO;
import com.udea.banco2025.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    CustomerDTO toDTO(Customer customer);
    Customer toEntity(CustomerDTO customerDTO);
}
