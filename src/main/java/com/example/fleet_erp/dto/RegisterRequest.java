package com.example.fleet_erp.dto;

import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {

  private String email;
  private String password;
  private String username;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private Set<String> roles;
}
