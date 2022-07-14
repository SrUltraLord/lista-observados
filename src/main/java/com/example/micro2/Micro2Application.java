package com.example.micro2;

import com.example.micro2.model.Account;
import com.example.micro2.model.Client;
import com.example.micro2.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class Micro2Application {

  public static void main(String[] args) {
    SpringApplication.run(Micro2Application.class, args);
  }

  @Bean
  CommandLineRunner runner(AccountRepository repository) {
    return args -> {
      Client client1 =
          Client.builder().nui("1722466420").fullName("David Reyes").state("ACT").build();
      Client client2 =
          Client.builder().nui("1722466421").fullName("Richard Hendricks").state("ACT").build();
      Client client3 =
          Client.builder().nui("1722466422").fullName("Erlich Bachman").state("ACT").build();
      Client client4 =
          Client.builder().nui("1722466423").fullName("Jared Joestar").state("ACT").build();
      Client client5 =
          Client.builder().nui("1722466424").fullName("Johnny Joestar").state("ACT").build();

      //      repository.saveAll(List.of(client1, client2, client3, client4, client5));

      Account acc1 =
          Account.builder()
              .internalCode(121)
              .clientId("62cf6668607b282906298cc4")
              .balance(BigDecimal.valueOf(300))
              .build();
      Account acc2 =
          Account.builder()
              .internalCode(122)
              .clientId("62cf6668607b282906298cc5")
              .balance(BigDecimal.valueOf(300))
              .build();
      Account acc3 =
          Account.builder()
              .internalCode(123)
              .clientId("62cf6668607b282906298cc6")
              .balance(BigDecimal.valueOf(300))
              .build();
      Account acc4 =
          Account.builder()
              .internalCode(124)
              .clientId("62cf6668607b282906298cc7")
              .balance(BigDecimal.valueOf(300))
              .build();
      Account acc5 =
          Account.builder()
              .internalCode(125)
              .clientId("62cf6668607b282906298cc8")
              .balance(BigDecimal.valueOf(300))
              .build();

      //      repository.saveAll(List.of(acc1, acc2, acc3, acc4, acc5));
    };
  }
}
