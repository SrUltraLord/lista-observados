package com.example.micro2.config;

import com.example.micro2.process.ProcessLineTask;
import com.example.micro2.process.ReadLineTask;
import com.example.micro2.process.WriteLineTask;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class TaskletsConfig {
  private final JobBuilderFactory jobs;
  private final StepBuilderFactory steps;
  private final MongoTemplate mongoTemplate;

  @Bean
  protected Step readLines() {
    return steps.get("readLines").tasklet(new ReadLineTask()).build();
  }

  @Bean
  protected Step processLines() {
    return steps.get("processLines").tasklet(new ProcessLineTask(mongoTemplate)).build();
  }

  @Bean
  protected Step writeLines() {
    return steps.get("writeLines").tasklet(new WriteLineTask()).build();
  }

  @Bean
  public Job processTextFileJob() {
    return jobs.get("processTextFileJob")
        .start(readLines())
        .next(processLines())
        .next(writeLines())
        .build();
  }
}
