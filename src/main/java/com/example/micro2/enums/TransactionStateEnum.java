package com.example.micro2.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum TransactionStateEnum {
  EXECUTED("EJE", "Executed"),
  BLOCKED("BLO", "Blocked");

  private final String value;
  private final String text;
}
