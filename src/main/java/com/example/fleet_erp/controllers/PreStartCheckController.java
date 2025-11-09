package com.example.fleet_erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fleet_erp.dto.PrestartRequest;
import com.example.fleet_erp.dto.PrestartResponse;
import com.example.fleet_erp.services.PrestartService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RequestMapping("/api/prestart")
@RestController
public class PreStartCheckController {
    
    @Autowired
    private PrestartService prestartService;

    public ResponseEntity<Page> getPrestartChecks(

    @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "id")String sortBy, @RequestParam(defaultValue = "asc")String sortDir)
    {
        if(prestartService == null) return null;

        return ResponseEntity
        .ok(prestartService
        .getPrestartChecks(page,size, sortBy, sortDir)); 
    }

    @PostMapping
    public ResponseEntity<PrestartResponse> addPrestart(@RequestBody PrestartRequest req){
        PrestartResponse pr = prestartService
        .addPrestartCheck(req);
        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(pr);
    }

    


}
