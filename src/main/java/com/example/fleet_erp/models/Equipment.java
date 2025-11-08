package com.example.fleet_erp.models;

import com.example.fleet_erp.enums.EquipmentStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

// @AllArgsConstructor
// @NoArgsConstructor
@Table(name = "equipments")
public class Equipment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @NotNull
  @Column(unique = true)
  private String name;

  private String description;

  @NotNull private Integer mileage;

  @NotNull
  @Column(updatable = false)
  private LocalDateTime dateAdded = LocalDateTime.now();

  private LocalDateTime dateModified;

  private Integer nextMaintenanceHours;

  private EquipmentStatus status = EquipmentStatus.available;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private EquipmentCategory category;


  // One Equipment can have many PreStartChecks
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PreStartCheck> preStartChecks = new ArrayList<>();

  @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Maintenance> maintenances = new ArrayList<>();

  // Constructors
  public Equipment() {}

  public Equipment(String name, String description, Integer mileage, EquipmentCategory category) {
    this.name = name;
    this.description = description;
    this.mileage = mileage;
    this.category = category;
    this.dateAdded = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.dateModified = LocalDateTime.now();
  }

  // Getters and Setters
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getMileage() {
    return mileage;
  }

  public void setMileage(Integer mileage) {
    this.mileage = mileage;
  }

  public LocalDateTime getDateAdded() {
    return dateAdded;
  }

  public void setDateAdded(LocalDateTime dateAdded) {
    this.dateAdded = dateAdded;
  }

  public LocalDateTime getDateModified() {
    return dateModified;
  }

  public void setDateModified(LocalDateTime dateModified) {
    this.dateModified = dateModified;
  }

  public Integer getNextMaintenanceHours() {
    return nextMaintenanceHours;
  }

  public void setNextMaintenanceHours(Integer nextMaintenanceHours) {
    this.nextMaintenanceHours = nextMaintenanceHours;
  }

  public EquipmentCategory getCategory() {
    return category;
  }

  public void setCategory(EquipmentCategory category) {
    this.category = category;
  }
}
