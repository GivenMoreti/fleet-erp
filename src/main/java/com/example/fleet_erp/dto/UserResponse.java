package com.example.fleet_erp.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class UserResponse {
  private UUID id;
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private Set<String> roles;
  private String phoneNumber;
  private String status;

  private LocalDateTime createdAt;
}
