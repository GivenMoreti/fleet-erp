package com.example.fleet_erp.repository;

import com.example.fleet_erp.models.Equipment;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {

  Page<Equipment> findAll(Pageable pageable);

  // CUSTOM SQL QUERYING METHODS ARE POSSIBLE TOO (:
  @Query("SELECT e FROM Equipment e WHERE e.name LIKE %:name%")
  Page<Equipment> findByNameContaining(@Param("name") String name, Pageable pageable);

  boolean existsByName(String name);
}
