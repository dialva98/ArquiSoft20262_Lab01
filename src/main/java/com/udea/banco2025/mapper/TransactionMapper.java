/*
 * Copyright (c) 2026 Diego. All rights reserved.
 */

package com.udea.banco2025.mapper;
import com.udea.banco2025.DTO.TransactionDTO;
import com.udea.banco2025.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE= Mappers.getMapper(TransactionMapper.class);
    TransactionDTO toDTO(Transaction transaction);
}