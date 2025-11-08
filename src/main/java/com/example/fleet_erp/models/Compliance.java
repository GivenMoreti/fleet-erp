package com.example.fleet_erp.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

/* CHECKS IF THE OPERATOR CERTIFICATES ARE EXPIRING
 * EQUIPMENT PRESTART CHECKS ARE COMPLYING
 * NON-COMPLIANCE IN ANY OF THE ENTITIES RAISES AN ALERT FOR COMPLIANCE ISSUE
 *
 */

@Data
@AllArgsConstructor
public class Compliance {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private UserCertificate userCert; // fed from
  private PreStartCheck preStartCheck; // fed from

  private boolean compliant = false;
  private LocalDateTime createdAt;
}
