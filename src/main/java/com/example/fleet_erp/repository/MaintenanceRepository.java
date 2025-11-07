package com.example.fleet_erp.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.fleet_erp.models.Maintenance;


@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance,UUID> {
    
    Page<Maintenance> findAll(Pageable pageable);   

    @Query("SELECT m FROM MAINTENANCE m WHERE m.status LIKE %:status%")
    Page<Maintenance> findByStatusContaining(@Param("status") String status,Pageable pageable);
    
    
    @Query("SELECT m FROM MAINTENANCE m WHERE m.title LIKE %:title%")
    Page<Maintenance> findByTitleContaining(@Param("title") String title,Pageable pageable);
    

    
    @Query("SELECT m FROM MAINTENANCE m WHERE m.type LIKE %:type%")
    Page<Maintenance> findByTypeContaining(@Param("type") String type,Pageable pageable);

    
    boolean existsByTitle(String title);
}
