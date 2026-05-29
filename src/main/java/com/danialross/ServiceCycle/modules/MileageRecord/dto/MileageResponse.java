package com.danialross.ServiceCycle.modules.MileageRecord.dto;

import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class MileageResponse {
    @Id
    @GeneratedValue
    @Schema(description = "UUID of the mileage record")
    private UUID id;

    @Column(nullable = false)
    @Schema(description = "mileage of vehicle when record was")
    private Integer mileage;

    @Column(nullable = false)
    @Schema(description = "date when record was")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "vehicle_id",nullable = false)
    @Schema(description = "Vehicle of the record")
    private Vehicle vehicle;
}
