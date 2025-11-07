package com.example.fleet_erp.services;

import com.example.fleet_erp.dto.EquipmentRequest;
import com.example.fleet_erp.dto.EquipmentResponse;
import com.example.fleet_erp.enums.EquipmentCategoryEnums;
import com.example.fleet_erp.mappers.EquipmentMapper;
import com.example.fleet_erp.models.Equipment;
import com.example.fleet_erp.models.EquipmentCategory;
import com.example.fleet_erp.repository.CategoryRepository;
import com.example.fleet_erp.repository.EquipmentRepository;
import java.util.List;
import java.util.Optional;
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
public class EquipmentService {

  @Autowired private EquipmentRepository repository;
  @Autowired private CategoryRepository categoryRepository;

  public Page<EquipmentResponse> getEquipment(int page, int size, String sortBy, String sortDir) {

    Sort sort =
        sortDir.equalsIgnoreCase((Sort.Direction.ASC.name()))
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Equipment> equipments = repository.findAll(pageable);
    Page<EquipmentResponse> equipmentResponse = equipments.map(EquipmentMapper::toDto);

    return equipmentResponse;
  }

  public Page<EquipmentResponse> searchEquipments(
      String keyword, int page, int size, String sortBy, String sortDir) {
    Sort sort =
        sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Equipment> equipments = repository.findByNameContaining(keyword, pageable);

    return equipments.map(EquipmentMapper::toDto);
  }

  // GET ALL WITH DEFAULT SORTING
  public List<EquipmentResponse> getAllEquipmentSorted(String sortBy, String sortDir) {

    Sort sort =
        sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

    List<Equipment> equipments = repository.findAll(sort);

    return equipments.stream().map(EquipmentMapper::toDto).collect(Collectors.toList());
  }

  /*  ALLOWS YOU TO ADD A NEW FLEET TYPE INTO THE DATABASE
      ALL THE BUSINESS LOGIC GOES IN HERE
  */

  public EquipmentResponse addEquipment(EquipmentRequest req) {
    // Validate request
    if (req.getName() == null || req.getName().trim().isEmpty()) {
      throw new IllegalArgumentException("Equipment name cannot be null or empty");
    }

    // Check if equipment name already exists
    if (repository.existsByName(req.getName().toUpperCase())) {
      throw new IllegalArgumentException(
          "Equipment with name '" + req.getName() + "' already exists");
    }

    // Create equipment with basic fields first
    Equipment equipment = new Equipment();
    equipment.setName(req.getName().toUpperCase());
    equipment.setMileage(req.getMileage());
    equipment.setNextMaintenanceHours(req.getNextMaintenanceHours());

    // Save equipment first to get ID
    Equipment savedEquipment = repository.save(equipment);

    // Auto-update fields (description, category, etc.)
    Equipment updatedEquipment = autoUpdateFields(req, savedEquipment);
    log.info("Equipment is added successfully id {} " + req.getName());

    return EquipmentMapper.toDto(updatedEquipment);
  }

  private Equipment autoUpdateFields(EquipmentRequest req, Equipment equipment) {
    String categoryName, description;

    char firstChar = req.getName().toUpperCase().charAt(0);

    if (firstChar == 'E') {
      categoryName = EquipmentCategoryEnums.Excavator.toString();
      description = categoryName + " " + req.getName().toUpperCase();
    } else if (firstChar == 'T') {
      categoryName = EquipmentCategoryEnums.Truck.toString();
      description = categoryName + " " + req.getName().toUpperCase();
    } else if (firstChar == 'D') {
      categoryName = EquipmentCategoryEnums.DumpTruck.toString();
      description = categoryName + " " + req.getName().toUpperCase();
    } else if (firstChar == 'B') {
      categoryName = EquipmentCategoryEnums.Bakkie.toString();
      description = categoryName + " " + req.getName().toUpperCase();
    } else if (firstChar == 'V') {
      categoryName = EquipmentCategoryEnums.Vehicle.toString();
      description = categoryName + " " + req.getName().toUpperCase();
    } else {
      categoryName = EquipmentCategoryEnums.Other.toString();
      description = categoryName + " " + req.getName().toUpperCase();
    }

    // Find or create category
    EquipmentCategory category =
        categoryRepository
            .findByCategory(categoryName)
            .orElseGet(
                () -> {
                  EquipmentCategory newCategory = new EquipmentCategory(categoryName);
                  return categoryRepository.save(newCategory);
                });

    // Update equipment fields
    equipment.setDescription(description);
    equipment.setCategory(category);

    // Save and return updated equipment
    return repository.save(equipment);
  }

  public EquipmentResponse updateEquipment(EquipmentRequest req, UUID id) {
    // Find existing equipment
    Equipment existingEquip =
        repository
            .findById(id)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Equipment with id  " + id.toString() + " does not exist"));

    // Check if name is being changed and if new name already exists
    if (!existingEquip.getName().equals(req.getName().toUpperCase())) {
      if (repository.existsByName(req.getName().toUpperCase())) {
        throw new IllegalArgumentException(
            "Equipment with name '" + req.getName() + "' already exists");
      }
    }

    // Update basic fields
    existingEquip.setName(req.getName().toUpperCase());
    existingEquip.setMileage(req.getMileage());
    existingEquip.setNextMaintenanceHours(req.getNextMaintenanceHours());

    // Auto-update description and category based on new name
    Equipment updatedEquipment = autoUpdateFields(req, existingEquip);
    log.info("equipment with {} id " + req.getName() + "  is updated successfully");

    return EquipmentMapper.toDto(updatedEquipment);
  }

  // PERMANENTLY REMOVE EQUIPMENT FROM DB
  public void deleteEquipment(UUID id) {

    Optional<Equipment> equipToRemove = repository.findById(id);
    if (equipToRemove == null)
      throw new IllegalArgumentException("Equipment not found with id: " + id);

    log.warn("equipment with id {}", id.toString() + "  is deleted");

    repository.deleteById(id);
  }
}
