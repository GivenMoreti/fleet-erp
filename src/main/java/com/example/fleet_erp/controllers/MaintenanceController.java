package com.example.fleet_erp.controllers;

import com.example.fleet_erp.dto.MaintenanceRequest;
import com.example.fleet_erp.dto.MaintenanceResponse;
import com.example.fleet_erp.services.MaintenanceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/maintenances")
@RestController
public class MaintenanceController {

  @Autowired private MaintenanceService service;

  @GetMapping
  public ResponseEntity<Page> getMaintenances(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = " asc") String sortDir) {

    if (service == null) return null;

    return ResponseEntity.ok(service.getMaintenances(page, size, sortBy, sortDir));
  }

  @GetMapping("/sorted")
  public ResponseEntity<List<MaintenanceResponse>> getMaintenancesSorted(
      @RequestParam(defaultValue = "title") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir) {
    List<MaintenanceResponse> maintenances = service.getAllMaintenances(sortBy, sortDir);
    return ResponseEntity.ok(maintenances);
  }

  @PostMapping
  public ResponseEntity<MaintenanceResponse> addMaintenance(@RequestBody MaintenanceRequest req) {

    MaintenanceResponse response = service.addMaintenance(req);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
