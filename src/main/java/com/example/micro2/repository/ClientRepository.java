package com.example.micro2.repository;

import com.example.micro2.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, String> {}
