package com.danialross.ServiceCycle.modules.vehicles.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateVehicleDTO {
    @NotBlank(message = "Vehicle Id is required")
    String Id;

    String make;

    String model;

    String licensePlate;

    @PositiveOrZero(message = "Mileage must be more than 0")
    Integer mileage;

    @Pattern(regexp = "CAR|MOTORCYCLE", message = "Invalid vehicle type. Valid options: CAR, MOTORCYCLE")
    String type;
}
