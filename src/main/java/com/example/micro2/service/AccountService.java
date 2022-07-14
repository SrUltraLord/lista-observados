package com.example.micro2.service;

import com.example.micro2.exception.NotFoundException;
import com.example.micro2.model.Account;
import com.example.micro2.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {
  private final AccountRepository accountRepository;

  public Account findByInternalCode(Integer internalCode) {
    return accountRepository
        .findByInternalCode(internalCode)
        .orElseThrow(
            () ->
                new NotFoundException("No se ha encontrado una cuenta con el id " + internalCode));
  }

  public void withdrawAmountFromAccount(BigDecimal amount, Account account) {
    BigDecimal previousBalance = account.getBalance();
    BigDecimal newBalance = previousBalance.subtract(amount);

    account.setBalance(newBalance);

    accountRepository.save(account);
  }

  public void depositAmountIntoAccount(BigDecimal amount, Account account) {
    BigDecimal previousBalance = account.getBalance();
    BigDecimal newBalance = previousBalance.add(amount);

    account.setBalance(newBalance);

    accountRepository.save(account);
  }
}
