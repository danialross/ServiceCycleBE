package com.danialross.ServiceCycle.modules.MaintenanceRecord;

import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "maintenance_record")
public class MaintenanceRecord {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Column(nullable = false)
    @Schema(description = "mileage at which maintenance was done at")
    private Integer changeAt;

    @Column
    @Schema(description = "The date the maintenance was done on")
    LocalDate doneTs;
}
