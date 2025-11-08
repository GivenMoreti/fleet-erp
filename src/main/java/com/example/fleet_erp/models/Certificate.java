package com.example.fleet_erp.models;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Certificate {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    private String certificateName;
    private String certificateNumber;
    
    private LocalDate issueDate;
    private LocalDate expiryDate;
}
