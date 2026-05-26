package com.danialross.ServiceCycle.modules.MaintenanceRecord;

import com.danialross.ServiceCycle.modules.parts.Part;
import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "maintenance_record")
public class MaintenanceRecord {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id",nullable = false)
    private Vehicle vehicle;

    @Column(nullable = false)
    @Schema(description = "mileage at which maintenance was done at")
    private Integer vehicleMileage;

    @Column(nullable = false)
    @Schema(description = "The date the maintenance was done on")
    private LocalDate date;

    @Column(nullable = false)
    @OneToMany(mappedBy = "maintenanceRecord",cascade = CascadeType.ALL)
    private List<Part> parts;

    @Column
    private String description;
}
