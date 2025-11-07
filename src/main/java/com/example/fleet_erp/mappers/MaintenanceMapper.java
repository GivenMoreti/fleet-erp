package com.example.fleet_erp.mappers;

import com.example.fleet_erp.dto.MaintenanceRequest;
import com.example.fleet_erp.dto.MaintenanceResponse;
import com.example.fleet_erp.models.Maintenance;

public class MaintenanceMapper {

  public static MaintenanceResponse toDto(Maintenance maintenance) {

    MaintenanceResponse response = new MaintenanceResponse();

    response.setCreatedAt(maintenance.getCreatedAt());
    response.setDescription(maintenance.getDescription());
    response.setDowntimeHours(maintenance.getDowntimeHours());
    response.setEndTime(maintenance.getEndTime());
    response.setId(maintenance.getId());
    response.setStartTime(maintenance.getStartTime());
    response.setStatus(maintenance.getStatus());
    response.setTitle(maintenance.getTitle());
    response.setType(maintenance.getType());
    response.setUpdatedAt(maintenance.getUpdatedAt());

    return response;
  }

  public static Maintenance toModel(MaintenanceRequest req) {

    Maintenance model = new Maintenance();
    model.setDescription(req.getDescription());
    model.setTitle(req.getTitle());
    model.setStartTime(req.getStartTime());
    model.setType(req.getType());
    model.setStatus(req.getStatus());
    model.setEndTime(req.getEndTime());

    return model;
  }
}
