package com.example.fleet_erp.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
  public LoginResponse(String token2, String username2, String email2, Set<String> roles2) {
    // TODO Auto-generated constructor stub
  }

  private String token;
  private String type = "Bearer";
  private String username;
  private String email;
  private Set<String> roles;
}
