package com.example.fleet_erp.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.example.fleet_erp.enums.ShiftStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="shifts")
public class Shift {
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name; // MORNING, AFTERNOON, NIGHT
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate shiftDate;
    
    //many shifts can be managed by one supervisor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id")
    private User supervisor;
    
    private Double targetProduction;
    
    @Enumerated(EnumType.STRING)
    private ShiftStatus status = ShiftStatus.UPCOMING; // SCHEDULED, IN_PROGRESS, COMPLETED
    
    
    private String handoverNotes;
    private LocalDateTime createdAt;

        // One Shift can have many PreStartChecks
    @OneToMany(mappedBy = "shift", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PreStartCheck> preStartChecks = new ArrayList<>();
}
