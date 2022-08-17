package com.example.micro2.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TransactionDTO implements Serializable {
  private Integer accountOrigin;
  private Integer accountDestination;
  private BigDecimal amount;
  private String state;
}
