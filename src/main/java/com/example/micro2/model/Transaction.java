package com.example.micro2.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "transactions")
@TypeAlias("transactions")
@Data
@Builder
public class Transaction {
  @Id private String id;

  @Indexed(name = "idxu_transactions_internalCode", unique = true)
  private String internalCode;

  private Date date;

  private Integer accountOrigin;

  private Integer accountDestination;

  private BigDecimal amount;

  private String state;
}
