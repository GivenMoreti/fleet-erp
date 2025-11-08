package com.example.fleet_erp.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCertificate {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private User user;
  private Certificate certificate;
  private String comments;
}
