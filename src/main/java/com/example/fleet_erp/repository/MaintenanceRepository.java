package com.example.fleet_erp.repository;

import com.example.fleet_erp.models.Maintenance;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, UUID> {

  Page<Maintenance> findAll(Pageable pageable);

  //  Page<Maintenance> findByStatus(@Param("status") MaintenanceStatus status, Pageable pageable);

  //  @Query("SELECT m FROM Maintenance m WHERE m.title LIKE %:title%")
  //   Page<Maintenance> findByTitleContaining(@Param("title") String title, Pageable pageable);

  // @Query("SELECT m FROM Maintenance m WHERE m.type LIKE %:type%")
  // Page<Maintenance> findByTypeContaining(@Param("type") String type, Pageable pageable);

  boolean existsByTitle(String title);
}
