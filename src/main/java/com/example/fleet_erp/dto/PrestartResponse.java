package com.example.fleet_erp.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.fleet_erp.models.CheckListStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrestartResponse {

  private UUID id;
  private UUID equipmentID;
  private UUID shiftId;
  private String username; // for now, later replace with user obj
  private boolean oilLevel;
  private boolean engine;
  private int mileage; // mileage of equipment
  private String defectsReported;
  private CheckListStatus overallStatus;
  private String notes;
  private LocalDateTime createdAt;
  public void setEquipmentId(UUID id2) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setEquipmentId'");
  }
}
