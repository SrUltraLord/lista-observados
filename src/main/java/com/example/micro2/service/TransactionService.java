package com.example.micro2.service;

import com.example.micro2.enums.TransactionStateEnum;
import com.example.micro2.exception.TransactionException;
import com.example.micro2.model.Account;
import com.example.micro2.model.Client;
import com.example.micro2.model.Transaction;
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

  private final AccountService accountService;
  private final ClientService clientService;
  private final NarcClientService narcClientService;
  private final TransactionRepository transactionRepository;

  public Transaction makeTransaction(Transaction transaction) {
    Account accountOrigin = accountService.findByInternalCode(transaction.getAccountOrigin());
    Account accountDestination =
        accountService.findByInternalCode(transaction.getAccountDestination());

//    Client clientOrigin = clientService.findById(accountOrigin.getClientId());
    Client clientDestination = clientService.findById(accountDestination.getClientId());

    boolean accountOriginHasEnougFunds =
        accountOrigin.getBalance().compareTo(transaction.getAmount()) >= 0;
    if (!accountOriginHasEnougFunds) {
      throw new TransactionException(
          "La cuenta " + accountOrigin.getInternalCode() + " no tiene fondos suficientes");
    }

    boolean isAmountGreaterThanMinCheck =
        transaction.getAmount().compareTo(MIN_TRANSFER_CHECK_AMOUNT) > 0;
    if (isAmountGreaterThanMinCheck) {
      boolean isDestinataryNarc =
          narcClientService.findNarcByName(clientDestination.getFullName()).getIsPenalized();
      if (isDestinataryNarc) {
        transaction.setInternalCode(UUID.randomUUID().toString());
        transaction.setDate(new Date());
        transaction.setState(TransactionStateEnum.BLOCKED.getValue());

        return transactionRepository.save(transaction);
      }
    }

    accountService.withdrawAmountFromAccount(transaction.getAmount(), accountOrigin);
    accountService.depositAmountIntoAccount(transaction.getAmount(), accountDestination);

    transaction.setInternalCode(UUID.randomUUID().toString());
    transaction.setDate(new Date());
    transaction.setState(TransactionStateEnum.EXECUTED.getValue());

    return transactionRepository.save(transaction);
  }
}
