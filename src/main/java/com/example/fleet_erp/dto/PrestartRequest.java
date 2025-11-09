package com.example.fleet_erp.dto;

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
public class PrestartRequest {

  private UUID equipmentId;
  private UUID shiftId;
  private String username; // for now, later replace with user obj
  private boolean oilLevel;
  private boolean engine;
  private int mileage; // mileage of equipment
  private String defectsReported;
  private CheckListStatus overallStatus;
  private String notes;
  // private LocalDateTime createdAt;

   public boolean getEngine(){
    return this.engine;
  }

    public boolean getOilLevel(){
    return this.oilLevel;
  }

  public void setEngine(boolean engine){
    this.engine = engine;
  }

  public void setOilLevel(boolean oil){
    this.oilLevel = oil;
  }
}
