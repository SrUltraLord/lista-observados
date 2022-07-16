package com.example.micro2.service;

import com.example.micro2.config.NarcClientConfig;
import com.example.micro2.dto.NarcDTO;
import com.example.micro2.enums.TransactionStateEnum;
import com.example.micro2.exception.NotFoundException;
import com.example.micro2.exception.TransactionException;
import com.example.micro2.model.Account;
import com.example.micro2.model.Client;
import com.example.micro2.model.Transaction;
import com.example.micro2.repository.AccountRepository;
import com.example.micro2.repository.ClientRepository;
import com.example.micro2.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
  private static final BigDecimal MIN_TRANSFER_CHECK_AMOUNT = BigDecimal.valueOf(1_000);

  private final NarcClientConfig clientConfig;
  private final AccountRepository accountRepository;
  private final ClientRepository clientRepository;
  final TransactionRepository transactionRepository;

  public Transaction makeTransaction(Transaction transaction) {
    Account accountOrigin = findAccountByInternalCode(transaction.getAccountOrigin());
    Account accountDestination = findAccountByInternalCode(transaction.getAccountDestination());

    Client clientDestination = findClientById(accountDestination.getClientId());

    boolean accountOriginHasEnoughFunds =
        accountOrigin.getBalance().compareTo(transaction.getAmount()) >= 0;
    if (!accountOriginHasEnoughFunds) {
      throw new TransactionException(
          "La cuenta " + accountOrigin.getInternalCode() + " no tiene fondos suficientes");
    }

    boolean isAmountGreaterThanMinCheck =
        transaction.getAmount().compareTo(MIN_TRANSFER_CHECK_AMOUNT) > 0;
    if (isAmountGreaterThanMinCheck) {
      boolean isClientNarc = findNarcByName(clientDestination.getFullName()).getIsPenalized();
      if (isClientNarc) {
        transaction.setInternalCode(UUID.randomUUID().toString());
        transaction.setDate(new Date());
        transaction.setState(TransactionStateEnum.BLOCKED.getValue());

        return transactionRepository.save(transaction);
      }
    }

    withdrawAmountFromAccount(transaction.getAmount(), accountOrigin);
    depositAmountIntoAccount(transaction.getAmount(), accountDestination);

    transaction.setInternalCode(UUID.randomUUID().toString());
    transaction.setDate(new Date());
    transaction.setState(TransactionStateEnum.EXECUTED.getValue());

    return transactionRepository.save(transaction);
  }

  public Account findAccountByInternalCode(Integer internalCode) {
    return accountRepository
        .findByInternalCode(internalCode)
        .orElseThrow(
            () ->
                new NotFoundException("No se ha encontrado una cuenta con el id " + internalCode));
  }

  private Client findClientById(String clientId) {
    return clientRepository
        .findById(clientId)
        .orElseThrow(
            () -> new NotFoundException("No se ha encontrado un cliente con el id " + clientId));
  }

  public NarcDTO findNarcByName(String fullName) {
    return clientConfig
        .getClient()
        .get()
        .uri(uriBuilder -> uriBuilder.path("/search").queryParam("fullName", fullName).build())
        .retrieve()
        .bodyToMono(NarcDTO.class)
        .block();
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
