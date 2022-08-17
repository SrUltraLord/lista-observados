package com.example.micro2.mapper;

import com.example.micro2.dto.TransactionDTO;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class TransactionFieldSetMapper implements FieldSetMapper<TransactionDTO> {
  public TransactionDTO mapFieldSet(FieldSet fieldSet) {
    return TransactionDTO.builder()
        .accountOrigin(fieldSet.readInt(0))
        .accountDestination(fieldSet.readInt(1))
        .amount(fieldSet.readBigDecimal(2))
        .build();
  }
}
