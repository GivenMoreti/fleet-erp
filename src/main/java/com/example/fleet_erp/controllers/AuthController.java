package com.example.fleet_erp.controllers;

import com.example.fleet_erp.dto.LoginRequest;
import com.example.fleet_erp.dto.LoginResponse;
import com.example.fleet_erp.dto.RegisterRequest;
import com.example.fleet_erp.dto.UserResponse;
import com.example.fleet_erp.services.AuthService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

  @Autowired private AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    LoginResponse response = authService.login(loginRequest);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/register")
  public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest registerRequest) {
    UserResponse response = authService.register(registerRequest);
    return ResponseEntity.ok(response);
  }
}
