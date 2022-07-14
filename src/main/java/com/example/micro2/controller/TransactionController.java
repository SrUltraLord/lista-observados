package com.example.micro2.controller;

import com.example.micro2.dto.TransactionDTO;
import com.example.micro2.model.Transaction;
import com.example.micro2.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;

  @PostMapping
  public Transaction makeTransaction(@RequestBody TransactionDTO dto) {
    return transactionService.makeTransaction(
        Transaction.builder()
            .accountOrigin(dto.getAccountOrigin())
            .accountDestination(dto.getAccountDestination())
            .amount(dto.getAmount())
            .build());
  }
}
