package com.example.fleet_erp.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.fleet_erp.models.PreStartCheck;

@Repository
public interface PrestartCheckRepository extends JpaRepository<PreStartCheck,UUID> {
      Page<PreStartCheck> findAll(Pageable pageable);
}
