package com.example.micro2.process;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class WriteLineTask implements Tasklet, StepExecutionListener {
  @Override
  public void beforeStep(StepExecution stepExecution) {
    log.info("ReadLineTask: Before");
  }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {

    log.info("ReadLineTask: Execute");

    return RepeatStatus.FINISHED;
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {

    log.info("ReadLineTask: After");

    return ExitStatus.COMPLETED;
  }
}
