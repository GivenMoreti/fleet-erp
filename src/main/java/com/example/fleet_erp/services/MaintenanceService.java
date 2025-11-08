package com.example.fleet_erp.services;

import com.example.fleet_erp.dto.*;
import com.example.fleet_erp.mappers.MaintenanceMapper;
import com.example.fleet_erp.models.Equipment;
import com.example.fleet_erp.models.Maintenance;
import com.example.fleet_erp.repository.EquipmentRepository;
import com.example.fleet_erp.repository.MaintenanceRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MaintenanceService {

  @Autowired private MaintenanceRepository maintenanceRepository;
  @Autowired private EquipmentRepository equipmentRepository;

  public Page<MaintenanceResponse> getMaintenances(
      int page, int size, String sortBy, String sortDir) {
    Sort sort =
        sortDir.equalsIgnoreCase((Sort.Direction.ASC.name()))
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Maintenance> maintenances = maintenanceRepository.findAll(pageable);
    Page<MaintenanceResponse> maintenanceResponse = maintenances.map(MaintenanceMapper::toDto);

    return maintenanceResponse;
  }

  // Get all maintenances with default sorting
  public List<MaintenanceResponse> getAllMaintenances(String sortBy, String sortDir) {
    Sort sort =
        sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

    List<Maintenance> maintenances = maintenanceRepository.findAll(sort);

    return maintenances.stream().map(MaintenanceMapper::toDto).collect(Collectors.toList());
  }

  // add new maintenance
  public MaintenanceResponse addMaintenance(MaintenanceRequest req) {
    if (req.getTitle().trim().isEmpty() || req.getStartTime() == null) {

      log.warn("Failed to add maintenance {} " + req.getTitle());

      throw new IllegalArgumentException("Maintenance title cannot be empty");
    }

    if (maintenanceRepository.existsByTitle(req.getTitle().toLowerCase())) {

      log.warn("Failed to add maintenance {} " + req.getTitle());

      throw new IllegalArgumentException("Maintenance " + req.getTitle() + " already exists");
    }

     if (req.getEquipmentId() == null) {
        log.warn("Failed to add maintenance {} - equipment ID is required", req.getTitle());
        throw new IllegalArgumentException("Equipment ID is required");
    }

      // Check if equipment exists
    Equipment equipment = equipmentRepository.findById(req.getEquipmentId())
        .orElseThrow(() -> {
            log.warn("Failed to add maintenance {} - equipment not found with ID: {}", 
                     req.getTitle(), req.getEquipmentId());
            return new IllegalArgumentException("Equipment not found with ID: " + req.getEquipmentId());
        });


      // create a new instance of maintenance
    Maintenance newMaintenance = new Maintenance();
    newMaintenance.setDescription(req.getDescription());
    newMaintenance.setDowntimeHours(req.getDowntimeHours());
    newMaintenance.setStartTime(req.getStartTime());
    newMaintenance.setStatus(req.getStatus());
    newMaintenance.setType(req.getType());
    newMaintenance.setEndTime(req.getEndTime());
    newMaintenance.setTitle(req.getTitle());

    newMaintenance.setEquipment(equipment);

    Maintenance savedMaintenance = maintenanceRepository.save(newMaintenance);
    log.info("Maintenance added successfully id {} " + req.getTitle());

    return MaintenanceMapper.toDto(savedMaintenance);
  }

  public MaintenanceResponse updateMaintenance(MaintenanceRequest req, UUID id) {

    // check if it exists first
    Maintenance existingMaintenance =
        maintenanceRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Maintenance with id " + req.getTitle() + " does not exists"));

    if (!existingMaintenance.getTitle().equals(req.getTitle())) {
      if (maintenanceRepository.existsByTitle(req.getTitle().toLowerCase())) {
        throw new IllegalArgumentException(
            "Maintenance with title '" + req.getTitle() + "' already exists");
      }
    }

    existingMaintenance.setDescription(req.getDescription());
    existingMaintenance.setDowntimeHours(req.getDowntimeHours());
    existingMaintenance.setStartTime(req.getStartTime());
    existingMaintenance.setStatus(req.getStatus());
    existingMaintenance.setType(req.getType());
    existingMaintenance.setEndTime(req.getEndTime());
    existingMaintenance.setCreatedAt(req.getCreatedAt());
    existingMaintenance.setUpdatedAt(req.getUpdatedAt());

    log.info("Maintenance with title {}" + req.getTitle() + " updated successfully");

    return MaintenanceMapper.toDto(existingMaintenance);
  }
}
