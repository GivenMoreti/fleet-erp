package com.example.fleet_erp.services;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.fleet_erp.repository.EquipmentRepository;
import com.example.fleet_erp.repository.MaintenanceRepository;
import com.example.fleet_erp.dto.*;
import com.example.fleet_erp.mappers.MaintenanceMapper;
import com.example.fleet_erp.models.Maintenance;;


@Service
public class MaintenanceService {
    
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    
    
    public org.springframework.data.domain.Page<MaintenanceResponse> getMaintenances(int page, int size, String sortBy,String sortDir)
    {
        Sort sort = sortDir.equalsIgnoreCase((Sort.Direction.ASC.name()))? 
        Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Maintenance> maintenances = maintenanceRepository.findAll(pageable);
        Page<MaintenanceResponse> maintenanceResponse = maintenances.map(MaintenanceMapper::toDto);

        return maintenanceResponse;
            
    }
}
