package com.example.fleet_erp.mappers;

import com.example.fleet_erp.dto.PrestartResponse;
import com.example.fleet_erp.models.PreStartCheck;

public class PreStartMapper {

    public static PrestartResponse toDto(PreStartCheck model) {
          PrestartResponse pr = new PrestartResponse();
          pr.setCreatedAt(model.getCreatedAt());
          pr.setDefectsReported(model.getDefectsReported());
          pr.setEngine(model.getEngine());
          pr.setId(model.getId());
          pr.setMileage(model.getMileage());
          pr.setNotes(model.getNotes());
          pr.setOilLevel(model.getOilLevel());
          pr.setOverallStatus(model.getOverallStatus());

          if(model.getEquipment().getId() != null){
            pr.setEquipmentId(model.getEquipment().getId());
          }

          if(model.getShift().getId() != null){
            pr.setShiftId(model.getShift().getId());
          }

          return pr;
    }

}
