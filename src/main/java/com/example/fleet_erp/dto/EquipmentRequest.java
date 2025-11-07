package com.example.fleet_erp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentRequest {

  @NotNull(message = "Name is required")
  @Column(unique = true, length = 50)
  private String name;

  @NotNull(message = "Mileage is required")
  private int mileage;

  @NotNull(message = "Next Maintenance Hours is required")
  private int nextMaintenanceHours;

  /* ALL OF THIS FIELDS ARE FILLED AUTOMATICALLY BY THE ADD SERVICE METHOD */
  // private EquipmentCategory category;

  // private LocalDate dateAdded;

  // private LocalDate dateModified;

  // private String description;

}
