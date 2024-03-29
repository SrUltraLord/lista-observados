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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class Micro2Application {

  @Autowired JobLauncher launcher;

  @Autowired
  @Qualifier("processTextFileJob")
  Job processTextFileJob;

  public static void main(String[] args) {
    SpringApplication.run(Micro2Application.class, args);
  }

  @Scheduled(fixedDelay = 2_000, initialDelay = 1_000)
//  @Scheduled(cron = "0 0 * * *") // Daily job
  public void performJob()
      throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
          JobParametersInvalidException, JobRestartException {
    log.info("Starting file processing job");

    JobParameters params =
        new JobParametersBuilder()
            .addString("ProcessTextFileJob", String.valueOf(System.currentTimeMillis()))
            .toJobParameters();

    launcher.run(processTextFileJob, params);
  }
}
