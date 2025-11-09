package com.example.fleet_erp.services;

import com.example.fleet_erp.dto.LoginRequest;
import com.example.fleet_erp.dto.LoginResponse;
import com.example.fleet_erp.dto.RegisterRequest;
import com.example.fleet_erp.dto.UserResponse;
import com.example.fleet_erp.mappers.UserMapper;
import com.example.fleet_erp.models.Role;
import com.example.fleet_erp.models.User;
import com.example.fleet_erp.repository.RoleRepository;
import com.example.fleet_erp.repository.UserRepository;
import com.example.fleet_erp.utils.JwtTokenUtil;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired private AuthenticationManager authManager;
  @Autowired private UserRepository userRepository;

  @Autowired private RoleRepository roleRepository;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired private CustomUserDetailsService userDetailsService;

  public LoginResponse login(LoginRequest req) {
    Authentication authentication =
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = userDetailsService.loadUserByUsername(req.getEmail());
    String token = jwtTokenUtil.generateToken(userDetails);

    User user =
        userRepository
            .findByUsername(req.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

    Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());

    return new LoginResponse(token, user.getUsername(), user.getEmail(), roles);
  }

  public UserResponse register(RegisterRequest registerRequest) {
    if (userRepository.existsByUsername(registerRequest.getUsername())) {
      throw new RuntimeException("User already exist");
    }

    if (userRepository.existsByEmail(registerRequest.getEmail())) {
      throw new RuntimeException("user with that email already exists");
    }

    User newUser = new User();
    newUser.setUsername(registerRequest.getUsername());
    newUser.setEmail(registerRequest.getEmail());
    newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    newUser.setFirstName(registerRequest.getFirstName());
    newUser.setLastName(registerRequest.getLastName());
    newUser.setPhoneNumber(registerRequest.getPhoneNumber());

    // set up roles
    Set<Role> roles = new HashSet<>();
    if (registerRequest.getRoles() == null || registerRequest.getRoles().toString().isEmpty()) {

      // default role
      Role userRole =
          roleRepository
              .findByName("OPERATOR")
              .orElseThrow(() -> new RuntimeException("Default role not found"));

      roles.add(userRole);
    } else {
      roles =
          registerRequest.getRoles().stream()
              .map(
                  roleName ->
                      roleRepository
                          .findByName(roleName)
                          .orElseThrow(() -> new RuntimeException("Role not found")))
              .collect(Collectors.toSet());
    }

    newUser.setRoles(roles);
    User savedUser = userRepository.save(newUser);

    return UserMapper.toUserResponse(savedUser);
  }
}
