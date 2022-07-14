package com.example.micro2.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clients")
@TypeAlias("clients")
@Data
@Builder
public class Client {
  @Id private String id;

  @Indexed(name = "idxu_clients_nui", unique = true)
  private String nui;

  @Indexed(name = "idx_clients_fullName")
  private String fullName;

  @Indexed(name = "idx_clients_state")
  private String state;
}
