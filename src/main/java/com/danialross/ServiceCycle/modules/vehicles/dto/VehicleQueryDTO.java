package com.danialross.ServiceCycle.modules.vehicles.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class VehicleQueryDTO {
    @Schema(description = "Make of Vehicle")
    String make;

    @Schema(description = "Model of Vehicle")
    String model;

    @Schema(description = "Type of Vehicle")
    @Pattern(regexp = "CAR|MOTORCYCLE", message = "Invalid vehicle type. Valid options: CAR, MOTORCYCLE")
    String type;

    @Schema(description = "Minimum mileage of Vehicle")
    @PositiveOrZero(message = "Mileage must be more than 0")
    Integer minMileage;

    @Schema(description = "Maximum mileage of Vehicle")
    @PositiveOrZero(message = "Mileage must be more than 0")
    Integer maxMileage;

    @Schema(description = "License plate of Vehicle")
    String licensePlate;
}
