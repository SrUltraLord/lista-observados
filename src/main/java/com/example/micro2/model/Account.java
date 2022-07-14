package com.example.micro2.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "accounts")
@TypeAlias("accounts")
@Data
@Builder
public class Account {
  @Id private String id;

  @Indexed(name = "idxu_accounts_internalCode", unique = true)
  private Integer internalCode;

  @Indexed(name = "idxu_accounts_clientId")
  private String clientId;

  private BigDecimal balance;

  private Date lastTransactionDate;
}
