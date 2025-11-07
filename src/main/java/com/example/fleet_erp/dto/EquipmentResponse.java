package com.example.fleet_erp.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentResponse {

  private UUID id;
  private String name, description, categoryName;
  private int mileage, nextMaintenanceHours;
  private LocalDateTime dateAdded, dateModified;
}
