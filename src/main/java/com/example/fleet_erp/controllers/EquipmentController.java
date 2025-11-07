package com.example.fleet_erp.controllers;

import com.example.fleet_erp.dto.EquipmentRequest;
import com.example.fleet_erp.dto.EquipmentResponse;
import com.example.fleet_erp.dto.MaintenanceResponse;
import com.example.fleet_erp.services.EquipmentService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/equipments")
@CrossOrigin("*")
public class EquipmentController {

  @Autowired private final EquipmentService equipmentService = null;

  @GetMapping
  public ResponseEntity<Page<EquipmentResponse>> getEquipments(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = " asc") String sortDir) {

    if (equipmentService == null) {
      return null;
    }
    return ResponseEntity.ok(equipmentService.getEquipment(page, size, sortBy, sortDir));
  }

  @GetMapping("/search")
  public ResponseEntity<Page<EquipmentResponse>> searchEquipments(
      @RequestParam String keyword,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "firstName") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir) {

    Page<EquipmentResponse> equipments =
        equipmentService.searchEquipments(keyword, page, size, sortBy, sortDir);
    return ResponseEntity.ok(equipments);
  }

  @GetMapping("/sorted")
  public ResponseEntity<List<EquipmentResponse>> getAllPatientsSorted(
      @RequestParam(defaultValue = "name") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir) {

    List<EquipmentResponse> equipments = equipmentService.getAllEquipmentSorted(sortBy, sortDir);
    return ResponseEntity.ok(equipments);
  }

  @GetMapping("/{id}/maintenances")
  public ResponseEntity<List<MaintenanceResponse>> getEquipmentsMaintenances(
      @PathVariable UUID id) {

    return ResponseEntity.ok(equipmentService.getEquipmentsMaintenances(id));
  }

  @PostMapping
  public ResponseEntity<EquipmentResponse> addEquipment(@RequestBody EquipmentRequest req) {

    EquipmentResponse response = equipmentService.addEquipment(req);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("{id}")
  public ResponseEntity<EquipmentResponse> updateEquipment(
      @RequestBody EquipmentRequest req, @PathVariable UUID id) {
    EquipmentResponse response = equipmentService.updateEquipment(req, id);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEquipment(@PathVariable UUID id) {

    equipmentService.deleteEquipment(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  // this also works
  /*
   * @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEquipment(@PathVariable UUID id){

      equipmentService.deleteEquipment(id);

      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
  }
   */

}
