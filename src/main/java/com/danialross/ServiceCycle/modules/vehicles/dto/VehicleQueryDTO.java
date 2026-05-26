package com.danialross.ServiceCycle.modules.vehicles.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class VehicleQueryDTO {
    String make;
    String model;

    @Pattern(regexp = "CAR|MOTORCYCLE", message = "Invalid vehicle type. Valid options: CAR, MOTORCYCLE")
    String type;

    @PositiveOrZero(message = "Mileage must be more than 0")
    Integer minMileage;

    @PositiveOrZero(message = "Mileage must be more than 0")
    Integer maxMileage;

    String licensePlate;
}
