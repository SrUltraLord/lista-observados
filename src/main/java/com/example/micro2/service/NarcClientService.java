package com.example.micro2.service;

import com.example.micro2.config.NarcClientConfig;
import com.example.micro2.dto.NarcDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NarcClientService {
  private final NarcClientConfig clientConfig;

  public NarcDTO findNarcByName(String fullName) {
    return clientConfig
        .getClient()
        .get()
        .uri(uriBuilder -> uriBuilder.path("/search").queryParam("fullName", fullName).build())
        .retrieve()
        .bodyToMono(NarcDTO.class)
        .block();
  }
}
