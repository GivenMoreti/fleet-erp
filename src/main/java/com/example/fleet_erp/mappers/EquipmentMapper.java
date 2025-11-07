package com.example.fleet_erp.mappers;

import com.example.fleet_erp.dto.EquipmentRequest;
import com.example.fleet_erp.dto.EquipmentResponse;
import com.example.fleet_erp.models.Equipment;

public class EquipmentMapper {

  public static EquipmentResponse toDto(Equipment equipments) {

    EquipmentResponse er = new EquipmentResponse();
    er.setId(equipments.getId());
    er.setDateAdded(equipments.getDateAdded());
    er.setDateModified(equipments.getDateModified());
    er.setDescription(equipments.getDescription());
    er.setMileage(equipments.getMileage());
    er.setName(equipments.getName());
    er.setNextMaintenanceHours(equipments.getNextMaintenanceHours());
    er.setCategoryName(
        equipments.getCategory() != null ? equipments.getCategory().getCategory() : null);

    return er;
  }

  public static Equipment toModel(EquipmentRequest eq) {

    Equipment e = new Equipment();
    // e.setDateAdded(eq.getDateAdded());
    // e.setDateModified(eq.getDateModified());
    e.setMileage(eq.getMileage());
    e.setName(eq.getName());
    e.setNextMaintenanceHours(eq.getNextMaintenanceHours());
    // e.setDescription(eq.getDescription());

    return e;
  }
}
