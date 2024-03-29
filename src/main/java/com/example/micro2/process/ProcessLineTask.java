package com.example.micro2.process;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.data.mongodb.core.MongoTemplate;

@RequiredArgsConstructor
@Slf4j
public class ProcessLineTask implements Tasklet, StepExecutionListener {
  private final MongoTemplate template;

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
