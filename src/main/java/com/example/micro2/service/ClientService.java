package com.example.micro2.service;

import com.example.micro2.exception.NotFoundException;
import com.example.micro2.model.Client;
import com.example.micro2.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
  private final ClientRepository clientRepository;

  public Client findById(String clientId) {
    return clientRepository
        .findById(clientId)
        .orElseThrow(
            () -> new NotFoundException("No se ha encontrado un cliente con el id " + clientId));
  }
}
