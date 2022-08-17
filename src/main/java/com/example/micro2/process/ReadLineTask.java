package com.example.micro2.process;

import com.example.micro2.dto.TransactionDTO;
import com.example.micro2.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ReadLineTask implements Tasklet, StepExecutionListener {

  private List<TransactionDTO> transactions;
  private FileUtils fileUtils;

  @Override
  public void beforeStep(StepExecution stepExecution) {
    transactions = new ArrayList<>();
    fileUtils = new FileUtils("transacciones.csv");

    log.info("ReadLineTask: Before -> Lines Reader Initialized");
  }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    log.info("ReadLineTask: Execute");

    TransactionDTO transaction = fileUtils.readLine();
    while (transaction != null) {
      log.info("Transaction={}", transaction);

      transactions.add(transaction);
      transaction = fileUtils.readLine();
    }

    return RepeatStatus.FINISHED;
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    fileUtils.closeReader();

    log.info("ReadLineTask: After");
    stepExecution.getJobExecution().getExecutionContext().put("transactions", transactions);

    log.info("ReadLineTask: Transactions added to job context");

    return ExitStatus.COMPLETED;
  }
}
