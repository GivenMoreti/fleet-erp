package com.example.fleet_erp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.fleet_erp.dto.PrestartRequest;
import com.example.fleet_erp.dto.PrestartResponse;
import com.example.fleet_erp.mappers.PreStartMapper;
import com.example.fleet_erp.models.PreStartCheck;
import com.example.fleet_erp.models.Shift;
import com.example.fleet_erp.repository.EquipmentRepository;
import com.example.fleet_erp.repository.PrestartCheckRepository;
import com.example.fleet_erp.repository.ShiftRepository;

import lombok.extern.slf4j.Slf4j;

import com.example.fleet_erp.models.Equipment;

@Service
@Slf4j
public class PrestartService {

    @Autowired
    private EquipmentRepository equipmentRepository;
    
    @Autowired
    private PrestartCheckRepository prestartRepository;

    @Autowired
    private ShiftRepository shiftRepository;



    public Page<PrestartResponse> getPrestartChecks(
    int page,int size,String sortBy,String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase((Sort.Direction.ASC.name()))?
        Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<PreStartCheck> prestartChecks = prestartRepository.findAll(pageable);
        Page<PrestartResponse> prestartCheckResponses = prestartChecks.map(PreStartMapper::toDto);
        return prestartCheckResponses;
    }

    public PrestartResponse addPrestartCheck(PrestartRequest req){

        //check if shift exists
        Shift shift = shiftRepository
        .findById(req.getShiftId())
        .orElseThrow(()-> new IllegalArgumentException("Shift wit that id deosnt exist"));


        //check if equipment exists
        Equipment equipment = equipmentRepository
        .findById(req.getEquipmentId())
        .orElseThrow(()-> new IllegalArgumentException("Equipment with the id does not exists"));

        PreStartCheck ps = new PreStartCheck();
        ps.setDefectsReported(req.getDefectsReported());
        ps.setNotes(req.getNotes());
        ps.setMileage(req.getMileage());
        ps.setOverallStatus(req.getOverallStatus());

        ps.setEquipment(equipment);
        ps.setShift(shift);
        ps.setUsername(req.getUsername());
    
        ps.setEngine(req.getEngine());
        ps.setOilLevel(req.getOilLevel());

        PreStartCheck savedPstart = prestartRepository.save(ps);
        log.info("PrestartCheck with id {} saved successfully", ps.getId());

        return PreStartMapper.toDto(savedPstart);
    }



}
