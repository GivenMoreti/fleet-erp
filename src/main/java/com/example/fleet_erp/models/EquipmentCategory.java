package com.example.fleet_erp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "equipment_category")
public class EquipmentCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @NotNull
  @Column(unique = true)
  private String category;

  public EquipmentCategory(String category) {
    this.category = category;
  }

  public EquipmentCategory(String category, String description) {
    this.category = category;
  }
}
