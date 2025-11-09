package com.example.fleet_erp.mappers;

import com.example.fleet_erp.dto.UserResponse;
import com.example.fleet_erp.models.Role;
import com.example.fleet_erp.models.User;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

  public static UserResponse toUserResponse(User user) {
    UserResponse response = new UserResponse();
    response.setId(user.getId());
    response.setUsername(user.getUsername());
    response.setEmail(user.getEmail());
    response.setFirstName(user.getFirstName());
    response.setLastName(user.getLastName());
    response.setPhoneNumber(user.getPhoneNumber());
    response.setStatus(user.getStatus().name());
    response.setCreatedAt(user.getCreatedAt());

    Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
    response.setRoles(roles);

    return response;
  }
}
