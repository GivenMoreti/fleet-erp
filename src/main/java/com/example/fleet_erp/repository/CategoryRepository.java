package com.example.fleet_erp.repository;

import com.example.fleet_erp.models.EquipmentCategory;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<EquipmentCategory, UUID> {
  Optional<EquipmentCategory> findByCategory(String category);
}
