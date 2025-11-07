package com.example.fleet_erp.dto;
import java.time.LocalDateTime;

import com.example.fleet_erp.enums.MaintenanceStatus;
import com.example.fleet_erp.enums.MaintenanceType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MaintenanceRequest {
    
    private String title;

    private String description;

    private MaintenanceType type= MaintenanceType.scheduled;
    private MaintenanceStatus status = MaintenanceStatus.scheduled;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Double downtimeHours;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
}
