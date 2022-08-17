package com.example.micro2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class Micro2Application {
  JobLauncher launcher;

  @Qualifier("processTextFileJob")
  Job processTextFileJob;

  public static void main(String[] args) {
    SpringApplication.run(Micro2Application.class, args);
  }

  @Scheduled(fixedDelay = 1_500, initialDelay = 5_000)
  public void performJob()
      throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
          JobParametersInvalidException, JobRestartException {
    log.info("Starting file processing job");

    JobParameters params =
        new JobParametersBuilder()
            .addString("ProcessTextFileJob", "File processing job")
            .toJobParameters();

    launcher.run(processTextFileJob, params);
  }
}
