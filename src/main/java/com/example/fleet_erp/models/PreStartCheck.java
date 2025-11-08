package com.example.fleet_erp.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
@Table(name="pre_start_check")
public class PreStartCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    //equipment id
    // Many PreStartChecks can belong to one Equipment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

   // Many PreStartChecks can belong to one Shift
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id")
    private Shift shift;

    // private Operator operator;  //driver/user accessing the fleet
    private String username;    //for now, later replace with user obj

    private boolean oilLevel;
    private boolean engine;
    private int mileage;        //mileage of equipment
    private String defectsReported;
    private CheckListStatus overallStatus = CheckListStatus.PASS; // PASS, FAIL
    
    private String notes;
    private LocalDateTime createdAt;

}
