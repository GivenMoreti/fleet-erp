package com.example.fleet_erp.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.fleet_erp.models.Shift;

@Repository
public interface ShiftRepository extends JpaRepository<Shift,UUID> {
    
}
