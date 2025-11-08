package com.example.fleet_erp.models;

import com.example.fleet_erp.enums.MaintenanceStatus;
import com.example.fleet_erp.enums.MaintenanceType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Maintenance")
public class Maintenance {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "equipment_id")
  private Equipment equipment; // many equipment can be on one maintenance instance

  @NotNull(message = "title is required")
  private String title;

  @NotNull(message = "description is required")
  private String description;

  private MaintenanceType type = MaintenanceType.scheduled;
  private MaintenanceStatus status = MaintenanceStatus.scheduled;

  private LocalDateTime startTime;
  private LocalDateTime endTime;

  private Double downtimeHours;

  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updatedAt;

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}
