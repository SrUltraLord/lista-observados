package com.example.micro2;

import com.example.micro2.service.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Micro2Application {

  public static void main(String[] args) {
    SpringApplication.run(Micro2Application.class, args);
  }

  @Bean
  CommandLineRunner runner(TransactionService transactionService) {
    return args -> {
      transactionService.readFile();
    };
  }
}
