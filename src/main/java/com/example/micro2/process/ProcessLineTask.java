package com.example.micro2.process;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.data.mongodb.core.MongoTemplate;

@RequiredArgsConstructor
public class ProcessLineTask implements Tasklet, StepExecutionListener {

  private final MongoTemplate mongoTemplate;

  @Override
  public void beforeStep(StepExecution stepExecution) {}

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    return null;
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    return null;
  }
}
