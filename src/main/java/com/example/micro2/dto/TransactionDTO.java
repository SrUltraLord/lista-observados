package com.example.micro2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
  private Integer accountOrigin;
  private Integer accountDestination;
  private BigDecimal amount;
}
