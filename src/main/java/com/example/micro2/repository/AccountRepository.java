package com.example.micro2.repository;

import com.example.micro2.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

  Optional<Account> findByInternalCode(Integer internalCode);
}
