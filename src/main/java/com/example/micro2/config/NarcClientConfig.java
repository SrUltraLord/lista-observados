package com.example.micro2.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NarcClientConfig {
  @Getter private final WebClient client;

  public NarcClientConfig(@Value("${com.example.micro2}") String baseUrl) {
    this.client = WebClient.create(baseUrl + "/narcs");
  }
}
